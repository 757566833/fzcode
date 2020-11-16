
# spring cloud

> spring cloud

## 启动
````
cd xx
mvn spring-boot:run -Dspring-boot.run.profiles=xxx
````

## 打包

```shell script
cd xx

mvn clean package -Dmaven.test.skip=true
```

## 运行

```shell script
cd xx 
java -jar ./target/file-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### 监听脚本
https://github.com/vishnubob/wait-for-it


### spring cloud config 坑

这里面引入了com.jcraft.jsch 
实际上这个包要读取~/.ssh/config文件

所以必须要有这个文件 大概内容是
```shell script
Host <your_git_host>
    HostName <your_git_host>
    IdentityFile ~/.ssh/<your_git_service/bot_user_private_key-id_rsa>
```

第二点是这玩意版本太低 不支持高版本  
7.8以前
```shell script
-----BEGIN RSA PRIVATE KEY-----
```

7.8以后
```shell script
-----BEGIN OPENSSH PRIVATE KEY-----
```
https://github.com/spring-cloud/spring-cloud-config/issues/1251#issuecomment-548015466