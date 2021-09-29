package com.fzcode.serviceauth.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fzcode.internalcommon.constant.RedisDBEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTemplateMap {
    @Value("${redis.host}")
    private String hostName;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String passWord;
    @Value("${redis.maxIdle}")
    private int maxIdl;
    @Value("${redis.minIdle}")
    private int minIdl;
    @Value("${redis.timeout}")
    private int timeout;

    public static Map<String, RedisTemplate<Serializable, Object>> redisTemplateMap = new HashMap<>();

    @PostConstruct
    public void initRedisTemp() throws Exception{
       System.out.println("###### START 初始化 Redis 连接池 START ######");
        redisTemplateMap.put(RedisDBEnum.AUTH.getValue(), redisTemplateObject(RedisDBEnum.AUTH.getCode()));
        redisTemplateMap.put(RedisDBEnum.Mail.getValue(),redisTemplateObject(RedisDBEnum.Mail.getCode()));

        System.out.println("###### END 初始化 Redis 连接池 END ######");
    }

    public RedisTemplate<Serializable, Object> redisTemplateObject(int dbIndex) throws Exception {
        RedisTemplate<Serializable, Object> redisTemplateObject = new RedisTemplate<Serializable, Object>();
        redisTemplateObject.setConnectionFactory(redisConnectionFactory(jedisPoolConfig(),dbIndex));
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }

    /**
     * 连接池配置信息
     * @return
     */
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxIdle(maxIdl);
        //最小空闲连接数
        poolConfig.setMinIdle(minIdl);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        //当池内没有可用的连接时，最大等待时间
        poolConfig.setMaxWaitMillis(10000);
        //------其他属性根据需要自行添加-------------
        return poolConfig;
    }
    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @return
     */
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig, int db) {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(hostName);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(db);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(passWord));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(port);
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    private void setSerializer(RedisTemplate<Serializable, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping( LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);

        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //在使用String的数据结构的时候使用这个来更改序列化方式
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer );
        template.setValueSerializer(stringSerializer );
        template.setHashKeySerializer(stringSerializer );
        template.setHashValueSerializer(stringSerializer );
    }

    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key,RedisDBEnum db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        if (exists(key,redisTemplate)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key,RedisTemplate<Serializable, Object> redisTemplate) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key,RedisDBEnum db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value,RedisDBEnum db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            System.out.println("set cache error");
        }
        return result;
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 返回0代表为永久有效
     */
    public long getExpire(String key, TimeUnit unit, RedisDBEnum db) {
        RedisTemplate<Serializable, Object> redisTemplate = getRedisTemplateByDb(db);
        return redisTemplate.getExpire(key, unit);
    }



    /**
     * 根据db 获取对应的redisTemplate实例
     * @param db
     * @return
     */
    public RedisTemplate<Serializable, Object> getRedisTemplateByDb(RedisDBEnum db){
        return redisTemplateMap.get(db.getValue());
    }
}
