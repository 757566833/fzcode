
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
