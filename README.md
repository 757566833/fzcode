
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


# sql
## 创建用户
```sql
CREATE USER `fzcode`@`%` IDENTIFIED BY 'fzcode_123';
```
## 建表
```sql
create database fzcode;
```
## 授权
```sql
GRANT ALL ON fzcode.* TO 'fzcode'@'%';
```

# 建表
```sql
-- 1.权限表和用户表
create table users(
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `expired` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  `register_type` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
);

create table authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);
-- 2. 用户组
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 3. 权限组
 CREATE TABLE `group_authorities` (
   `group_id` bigint(20) NOT NULL,
   `authority` varchar(50) NOT NULL,
   KEY `fk_group_authorities_group` (`group_id`),
   CONSTRAINT `fk_group_authorities_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 4.权限组成员
CREATE TABLE `group_members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_members_group` (`group_id`),
  CONSTRAINT `fk_group_members_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 5. 消息
CREATE TABLE `texts` (
  `nid` int(255) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```
