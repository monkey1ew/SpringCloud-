# SpringCloud学习

#### 1. 微服务架构理论

```java
微服务是一种架构模式，它提倡将应用程序划分成一组小的程序
```

#### 2. 什么是SpringCloud? 

```java
分布式微服务架构的一站式解决方法，是多种微服务架构落地技术的集合体
```

![image-20220326152659534](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326152659534.png)

![image-20220326153446426](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326153446426.png)



#### 3. SpringBoot和SpringCloud版本选型

**SpringBoot2.X版 和 SpringCloud H版**

**boot 和 cloud的版本依赖约束**

![image-20220326154843319](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326154843319.png)

![image-20220326161145180](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326161145180.png)



#### 4. 父工程Project新建

**4.1 idea新建project工作空间**

![](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326162656455.png)**2.注解配置**

![image-20220326163133415](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326163133415.png)

**3.java编译版本**

![image-20220326163318948](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326163318948.png)

**3.file type过滤**

![image-20220326163733715](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326163733715.png)

**4.父工程pom**

![image-20220326163944452](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220326163944452.png)

```java
<!--统一管理jar包版本-->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>12</maven.compiler.source>
    <maven.compiler.target>12</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <lombok.version>1.18.10</lombok.version>
    <log4j.version>1.2.17</log4j.version>
    <mysql.version>8.0.18</mysql.version>
    <druid.version>1.1.16</druid.version>
    <mybatis.spring.boot.version>2.1.1</mybatis.spring.boot.version>
  </properties>

```

```java
<!--子模块继承之后，提供作用：锁定版本+子module不用谢groupId和version-->
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-project-info-reports-plugin</artifactId>
        <version>3.0.0</version>
      </dependency>
      <!--spring boot 2.2.2-->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.2.2.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--spring cloud Hoxton.SR1-->
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>Hoxton.SR1</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    <!--spring cloud 阿里巴巴-->
      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>2.1.0.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--mysql-->
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
        <scope>runtime</scope>
      </dependency>
      <!-- druid-->
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
      </dependency>
      <!--mybatis-->
      <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${mybatis.spring.boot.version}</version>
      </dependency>
      <!--junit-->
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
      </dependency>
      <!--log4j-->
      <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>
```

**dependencyManagement** **和 dependencies**

```java
Maven使用dependencyManagement 元素来提供一种管理依赖版本号的方式
    作用：能让所有在子项目种引用一个依赖而不用显示的列出版本号gav
    	只是声明依赖，并不引入实现，子项目需要显示的声明需要引入的依赖(并且子项目没有指定版本号)
```

#### 5. Rest微服务工程构建

**5.1 支付模块构建**

cloud-provider-payment8001-微服务提供者支付Module模块

```java
微服务模块
1.建module
2.改pom
3.写yml
4.主启动
5.业务类
```

**编写yml**

```java
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service

  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/cloud?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: fqh

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.fqh.springcloud.
```

**封装返回前端json类信息**

```java
package com.fqh.springcloud.bean;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResult<T> {

//    404 not_found
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
//        this.code = code;
//        this.message = message;
        this(code, message, null);
    }
}
```

**javaBean**

```java
package com.fqh.springcloud.bean;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment implements Serializable {

    private Long id;
    private String serial;
}
```

**dao接口**

```java
package com.fqh.springcloud.dao
    
@Mapper
public interface PaymentDao {

    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
```

**controller层**

```java
@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(Payment payment) {
        int rows = paymentService.create(payment);
        log.info("**************插入结果: {}", rows);

        if (rows > 0) {
            return new CommonResult(200, "插入数据库成功", rows);
        }else {
            return new CommonResult(200, "插入数据库失败", null);
        }
    }
    
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);

        log.info("**************插入结果: {}", payment);

        if (payment != null) {
            return new CommonResult(200, "查询成功", payment);
        }else {
            return new CommonResult(444, "没有对应记录,查询的id: " + id, null);
        }
    }
}
```

**5.2 Devtools**

```java
//添加依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
    
//在父工程添加
    <!--springboot默认的build方式-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <addResources>true</addResources>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

![image-20220327162441834](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220327162441834.png)



**5.3 消费者订单模块**

**1 RestTemplate**

```java
RestTemplate提供了多种便捷访问远程Http服务的方法
是一种简单的restful服务模块，是spring提供的用于访问Rest服务的客户端模板工具类
```

```java
@Configuration
public class ApplicationContextConfig {
    //给容器注入restTemplate
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```

**2 远程调用8001服务**

```java
@Slf4j
@RestController
public class OrderController {

    public static final String PAYMENT_URL = "http://localhost:8001";
    
    @Resource
    private RestTemplate restTemplate;
    
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }
    
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }
}
```

#### 6. 工程重构

```
系统中有重复部分，重构
```

1. **新建cloud-api-commons 模块，放公共的代码**

   ![image-20220327210211092](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220327210211092.png)

2. **clean 然后 install**

3. **在各个微服务模块导入maven依赖**

   ```java
   <!--        引入自己定义的api通用包，使用PayMent javaBean-->
           <dependency>
               <groupId>com.fqh.springcloud</groupId>
               <artifactId>cloud-api-common</artifactId>
               <version>${project.version}</version>
           </dependency>
   ```

## Ⅰ 初级阶段 🤓

### 一 服务的注册与发现

#### Eureka的服务发现与调用

​	**服务架构图**![image-20220328162551446](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220328162551446.png)

#### 	1.1 单机EurekaServer模块

```java
1.导入依赖 pom
        <!--        eureka-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        
2.编写yml
	eureka:
  instance:
    hostname: eureka7002.com #eureka服务器的名称
  client:
    # false表示不向注册中心注册自已
    register-with-eureka: false
    # false表示自己就是注册中心，我的职责是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      # 设置与Eureka Server交互的地址查询服务都需要依赖这个地址
      defaultZone: http://eureka7001.com:7001/eureka/
      
3.主启动类 + @EnableEurekaServer注解 
@EnableEurekaServer
@SpringBootApplication
public class EurekaMain7002 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7002.class, args);
    }
}
```

#### 1.2 Eureka集群的配置

```java
1.修改 hosts文件
127.0.0.1 eureka7001.com
127.0.0.1 eureka7002.com

2.修改EurekaServer的yml配置 (EurekaServer互相守望)
	hostname: eureka7002.com #eureka服务器的名称
	# 设置与Eureka Server交互的地址查询服务都需要依赖这个地址
      defaultZone: http://eureka7001.com:7001/eureka/
```

**将微服务注册进Eureka集群**

```java
1.服务添加添加依赖client依赖
<!--        引入eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        
2.修改yml
eureka:
  client:
    # 表示自己是否注册进eurekaServer默认true
    register-with-eureka: true
    # 是否从EurekaServer抓取已有的信息，默认为true, 集群必须设置为true才能配置ribbon使用负载均衡
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka/7002.com:7002/eureka #集群
      
3.主启动类添加@EnableEurekaClient注解
@EnableEurekaClient
@SpringBootApplication
public class PaymentMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}

```

**成功界面**

![image-20220328165402325](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220328165402325.png)

**支付服务提供者集群配置**

```java
1.新建8002服务模块
2.修改yml的server.port: 8002
3.修改8001和8002的controller
	添加---> @Value("${server.port}")
    		private String serverPort;
4.修改80模块的controller
    //public static final String PAYMENT_URL = "http://localhost:8001"; ------改为微服务名称
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

```

![image-20220328171217740](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220328171217740.png)

**报错（该微服务名称下面可能有多个主机）**

```java
java.net.UnknownHostException: CLOUD-PAYMENT-SERVICE
    
解决：使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
   	在ApplicationContextConfig中
**************************************************************
	@LoadBalanced //轮询的负载机制(Ribbon)
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
```

#### 1.3 actuator微服务信息完善

```java
存在的问题:
	主机名称：服务名称的修改 （只暴露服务名称）
	希望ip显示
	
在微服务提供者的yml配置文件中的
eureka: 
	下面加上
	instance:
    	instance-id: payment8001
    	prefer-ip-address: true //显示ip
	
```

**更改效果**

![image-20220329200229571](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220329200229571.png)

#### 1.4 服务发现Discovery

```java
对于注册进eureka里面的微服务, 可以通过服务发现来获得该服务的信息
    
step1 : 修改payment8001的Controller
    
    @Resource
    private DiscoveryClient discoveryClient; //加入

    编写代码
@GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("**********element : {}", service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return discoveryClient;
    }

step2:   8001主启动类上 + @EnableDiscoveryClient注解
    
```

**后台显示**

![image-20220329202442953](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220329202442953.png)

#### 1.6 Eureka的自我保护机制

```java
Eureka Server将会尝试保护其服务注册表的信息，不再删除服务注册表中的数据，也就是不会销毁任何微服务
    CAP里面的AP分支 高可用性 + 分区容忍性
```

![image-20220329202810299](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220329202810299.png)

```
怎么禁止自我保护??
	1.注册中心eurekaServer端7001
	eureka:
		server:
    		enable-self-preservation: false
    		eviction-interval-timer-in-ms: 2000 //心跳2ms
    
	2.生产者客户端eurekaClient端8001
	eureka:
		  instance:
    		instance-id: payment8001
    		prefer-ip-address: true
#    Eureka客户端向服务提供端发送心跳的时间间隔(默认30s)
    		lease-renewal-interval-in-seconds: 1
#    Eureka服务在收到最后一次心跳后等待的时间上限制（默认90s）
    		lease-expiration-duration-in-seconds: 2
```

![image-20220330175323134](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220330175323134.png)



#### Zookeeper的服务发现与调用

![image-20220331172436492](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220331172436492.png)

#### 2.1 支付服务注册进zookeeper

```java
1.新建cloud-provider-payment8004模块服务提供者
    
2.pom文件中去掉Eureka添加zookeeper依赖
    <!--        spirngboot整合zookeeper客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-zookeeper-discovery</artifactId>
        </dependency>

3.编写yml配置文件
#注册到zookeeper服务器的支付服务器提供者端口号
server:
  port: 8004
#服务别名------注册zookeeper到注册中心名称
spring:
  application:
    name: cloud-provider-payment
  cloud:
    zookeeper:
      //connect-string: 192.168.159.138:2181 #连接虚拟机的zk有问题（未解决） 
       connect-string: 127.0.0.1:2181  #连接windows的zk
4.主启动类
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain8004 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class, args);
    }
}

5.启动后报错jar包冲突
    排除zk冲突后的新pom
   <!--        spirngboot整合zookeeper客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-zookeeper-discovery</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
	<!--        添加zookeeper3.4.9版本-->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
        </dependency>
        
```

```java
qes: Zookeeper注册的服务节点是临时还是持久????(临时)
    关闭服务节点后--->发现没有了序号
```

![image-20220331202109124](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220331202109124.png)

```java
//服务端Controller
@Slf4j
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/zk")
    public String paymentZk() {
        log.info("有人来call我了............");
        return "springcloud with zookeeper :" + serverPort
                + "\t" + UUID.randomUUID().toString();
    }
}
```

#### 2.2 订单服务注册进zookeeper

```java
1.新建消费者模块 cloud-consumerzk-order80
2.pom文件与8004服务模块一致
3.编写yml文件
server:
  port: 80

spring:
  application:
    name: cloud-consumer-order
  #    注册到zookeeper
  cloud:
    zookeeper:
      connect-string: 127.0.0.1:2181

```

```java
4.配置bean
@Configuration
public class ApplicationContextConfig {

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```

```java
5.编写controller
@Slf4j
@RestController
public class OrderZKController {

    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String paymentInfo() {
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/zk",String.class);
        return result;
    }
}

```

#### Consul的服务注册与发现

#### 3.1 安装并运行Consul 

```java
Consul是一个开源的分布式服务发现和配置管理系统，采用go语言开发
```

**启动Consul**

```
consul agent -dev
```

![image-20220401172528102](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220401172528102.png)

**效果图**

![image-20220401172618044](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220401172618044.png)

#### 3.2 服务提供者注册进Consul

```java
1.新建工程8006服务提供者
2.写pom
<!--        springCloud consul-Server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
3.写yml
server:
  port: 8006

spring:
  application:
    name: consul-provider-payment

# consul注册中心
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: ${spring.application.name}
4.主启动类
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain8006 {
    
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8006.class, args);
    }
}
5.controller
@Slf4j
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/consul")
    public String paymentZk() {
        log.info("有人来call我了............");
        return "springcloud with consul :" + serverPort
                + "\t" + UUID.randomUUID().toString();
    }
}
```

#### 3.3 服务消费者注册进Consul

```
新建cloud-consumerconsul-order80模块
```

![image-20220401181626284](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220401181626284.png)

#### 3.4 CAP

![img](https://bkimg.cdn.bcebos.com/pic/5bafa40f4bfbfbed9c15b19b72f0f736aec31f81?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U5Mg==,g_7,xp_5,yp_5/format,f_auto)



### 二 负载均衡服务调用

#### 1. Ribbon负载均衡服务调用

```java
Ribbon 是基于Netflix Ribbon实现的一套客户端 负载均衡工具
```

```java
LB负载均衡（Load Balance）是什么?
	简单的说就是将用户的请求平均的分配到多个服务上，从而系统达到（HA）高可用，常见的负载均衡软件有，Nginx，LVS，硬件 F5等
```

**Ribbon (本地负载) VS Nginx（服务端负载）**

```java
1.Nginx是服务器负载均衡，客户端的所有请求都会交给Nignx，然后由Nignx是实现转发请求，即负载均衡是由服务器实现的
2.Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术
```

#### 2. Ribbon + RestTemplate

![image-20220402153543118](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220402153543118.png)

```java
@GetMapping("/consumer/payment/getForEntity/{id}")
public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {

    ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    if (entity.getStatusCode().is2xxSuccessful()) {
        log.info(entity.getStatusCode() + "\t" + entity.getHeaders());
        return entity.getBody();
    }else {
        return new CommonResult<>(444, "操作失败");
    }
}
```

#### 3. Ribbon的负载规则

```java
ribbon默认采用轮询规则
```

**负载规则的IRule接口**

![image-20220402153909677](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220402153909677.png)

![image-20220402153717395](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220402153717395.png)

![image-20220402153730988](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220402153730988.png)

#### 4. Ribbon负载规则替换

```java
tips: 不能放在能被ComponentScan所扫描到的包里
```

**编写自定义负载Rule**

![image-20220402154611270](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220402154611270.png)

**主启动类中添加注解@RibbonClient**

```java
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
```

#### 5. Ribbon负载均衡算法原理

``` java
负载均衡算法:rest接口第几次请求 % 服务器集群总数量 = 实际调用服务器位置下标，每次服务重启后rest接口计数从1开始
   
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PATMENT-SERVICE")
    list[0] instances = 127.0.0.1:8002
    list[1] instances = 127.0.0.1:8001
    
    1 % 2 = 1 ---》index = 1 list.get(index)
    2 % 2 = 0 ---》index = 0 list.get(index)
    3 % 2 = 1 ---》index = 1 list.get(index)
    .....
    重启后又从1开始
```

#### 6. 轮询算法源码分析

```java
public Server choose(ILoadBalancer lb, Object key) {
    if (lb == null) {
        log.warn("no load balancer");
        return null;
    } else {
        Server server = null;
        int count = 0;

        while(true) {
            if (server == null && count++ < 10) {
                List<Server> reachableServers = lb.getReachableServers(); //获取所有可达到的服务器
                List<Server> allServers = lb.getAllServers();	//获取所有的服务器
                int upCount = reachableServers.size();
                int serverCount = allServers.size();
                if (upCount != 0 && serverCount != 0) {
                	//调用自增并且取模的方法----->传入服务器的数量----->返回下一个服务器的索引
                    int nextServerIndex = this.incrementAndGetModulo(serverCount); 
                    server = (Server)allServers.get(nextServerIndex); //获取nextServerIndex对应的服务器
                    if (server == null) { //服务器为null, 调用当前线程礼让一下
                        Thread.yield();
                    } else {
                        if (server.isAlive() && server.isReadyToServe()) { //如果是up状态并且是准备使用就返回
                            return server;
                        }

                        server = null; //返回null
                    }
                    continue;
                }

                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            if (count >= 10) {
                log.warn("No available alive servers after 10 tries from load balancer: " + lb);
            }

            return server;
        }
    }
}

private int incrementAndGetModulo(int modulo) {
    int current;
    int next;
    do {
        current = this.nextServerCyclicCounter.get(); //获取下一个服务器循环计数器
        next = (current + 1) % modulo; //当前请求次数 + 1 % 服务器的总数量
    } while(!this.nextServerCyclicCounter.compareAndSet(current, next)); //CAS+自旋锁

    return next; //返回应该去访问的服务器的索引
}
```

#### 7. 手写负载的轮询算法

```java
1.启动7001，7002集群启动
2.8001，8002微服务的controller改造
	@GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort; //返回服务端口
    }
```

**去掉ApplicationContextConfig的Bean中@LoadBalanced注解**

```java
//    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
```

**创建LoadBalancer接口 得到服务器实例**

![image-20220402170126957](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220402170126957.png)

**创建MyLB实现LoadBalancer接口**

```java
@Component
public class MyLB implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("*******第几次访问次数 = next: " + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
```

**编写OrderContrller代码**

```java
@GetMapping(value = "/consumer/payment/lb")
public String getPaymentLB() {
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
    if (instances == null || instances.size() <= 0) {
        return null;
    }
    ServiceInstance serviceInstance = loadBalancer.instances(instances);
    URI uri = serviceInstance.getUri();
    return restTemplate.getForObject(uri + "/payment/lb", String.class);
}
```

#### 8. CAS

```java
1.。。。。。。后面回头再看juc
```

### 三 OpenFeign服务接口调用

```java
1.Feign：是一个声明式的web服务端，让编写web客户端变得非常容易，只需创建一个接口并在接口上添加注解即可
-------完成对服务端的接口进行绑定
```

#### 1. openFegin服务调用

```java
微服务调用接口 + @FeignClient注解
1.新建cloud-consumer-fegin-order80模块
    
2.添加openfegin场景启动器
<!--        openfegin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
    
3.编写yml
server:
	port: 80
eureka:
  client:
    register-with-eureka: false
    service-url: http://eureka7001.com:7001/eureka,http://eureka/7002.com:7002/eureka #集群

4.主启动类
```

![image-20220405132521403](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405132521403.png)

**业务类**

```java
通过FeignClient注解
    去eureka注册中心找对接的服务接口的方法进行调用
```

![image-20220405133534829](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405133534829.png)

**测试成功并带有负载均衡功能**

![image-20220405134344497](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405134344497.png)

![image-20220405134451465](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405134451465.png)

#### 2. openFegin超时控制

```java
演示超时情况
1.服务提供方8001故意写暂停程序
	客户端返回报错
	
2.在yml中配置超时控制
# 设置feign客户端超时时间(OpenFeign默认支持ribbon)
ribbon:
#  指的是建立在链接所有的时间，适用于网络正常情况下，两端链接所用的时间()
  ReadTimeout: 5000
#  指的是建立连接后从服务器读到可用资源所用的时间()
  ConnectTimeout: 5000
```

**8001 服务端编写超时代码**

![image-20220405141258821](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405141258821.png)

**通过openFeign调用接口**

![](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405141333776.png)

#### 3. openFegin日志打印功能

```java
1.配置日志bean
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; //详细日志
    }
}
2.在yml中添加配置
logging:
  level: 
#    fegin 日志以什么级别监控哪个接口
    com.fqh.springcloud.service.PaymentFeignService: debug
```

**效果图**

![image-20220405142035114](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220405142035114.png)

## Ⅱ 中级阶段 😠

### 四 服务降级-熔断 Hystrix

```java
Hystrix式一个处理分布式系统的延迟和容错的开源库，在分布式系统中不可避免的会调用失败。比如超时，异常等。
```

#### 1. Hystrix重要概念

**服务降级**

```java
服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback
哪些情况会发现服务降级
    	1.程序运行异常
    	2.超时
    	3.服务熔断触发服务降级
    	4.线程池/信号量打满也会导致服务降级
```

**服务熔断**

```java
类比保险丝达到最大访问后，直接拒绝访问，然后调用服务降级的方法并返回友好提示
```

**服务限流**

```java
秒杀高并发等操作，严禁拥挤，大家排队，一秒中N个，有序进行-------后序有Sentinel
```

#### 2. Hystrix支付微服务构建

```java
1.新建moudle
cloud-provider-hystrix-payment8001

2.引入pom
<dependencies>
<!--        hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fqh.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

3.写yml
server:
  port: 8001
  
spring:
  application:
    name: cloud-provider-hystrix-payment

eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
    register-with-eureka: true
    fetch-registry: true
    
4.主启动类
@EnableEurekaClient
@SpringBootApplication
public class PaymentHystrixMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8001.class, args);
    }
}

```

**编写Service**

```java
@Service
public class PaymentService {


    /**
     * 正常访问ok的方法
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName()
                + " paymentInfo_OK: " + id + "\t" + "o(n-n)o哈哈";
    }

    /**
     * 模拟异常的方法
     * @param id
     * @return
     */
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName()
                + " paymentInfo_Timeout: " + id + "\t"
                + "o(n-n)o哈哈" + " 耗时: " + timeNumber + "s";
    }
}
```

**编写Controller**

```java
@Slf4j
@RestController
public class PaymentController {
    
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("********result: {}", result);
        return result;
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_Timeout(id);
        log.info("********result: {}", result);
        return result;
    }
}
```

#### 3. JMeter高并发压测

```java
//2w个请求找一个请求发现ok也变慢了
//tomcat的默认工作线程数被打满了，没有多余的线程来处理多余的请求--->Hystrix解决
```

```java
1.新建80消费者cloud-consumer-fegin-hystrix-order80
2.写pom
<!--        openfegin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!--        hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fqh.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
3.写yml
server:
  port: 80

eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
4.主启动类
@EnableFeignClients
@SpringBootApplication
public class OrderHystrixMain80 {

    public static void main(String[] args) {

        SpringApplication.run(OrderHystrixMain80.class, args);
    }
}
```

**Service代码**

```java
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT")
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
```

**Controller代码**

```java
@Slf4j
@RestController
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/consumer/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
}
```

```java
//20000线程又去打8001，消费者访问变慢
```

#### 4. 降级容错解决的维度要求

```java
1.超时导致服务器变慢
2.出错（宕机或程序运行出错）
3.解决
```

#### 5. 降级配置@HystrixCommand

```java
设置自身调用超时的峰值，峰值内可以正常运行
超过了需要有备用处理返回，作服务降级fallback
```

**业务类添加@HystrixCommand**

```java
1.一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMetho调用类中指定方法
```

```java
 @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler"
            , commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 5;
//        int age = 10 / 0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName()
                + " paymentInfo_Timeout: " + id + "\t"
                + "o(*￣▽￣*)ブ哈哈" + " 耗时: " + timeNumber + "s";
    }

    public String paymentInfo_TimeoutHandler(Integer id) {

        return "线程池：" + Thread.currentThread().getName()
                + " paymentInfo_TimeoutHandler: " + id + "\t"
                + "/(ㄒoㄒ)/~~";
    }
```

**主启动类激活**

```java
新加注解@EnableCircuitBreaker
```

**测试效果**

![image-20220407194343038](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220407194343038.png)

```java
运行异常和超时异常-----服务不可用------服务降级fallback
```

#### 6. 对客户端进行服务降级fallback

```java
1.80的yml配置文件添加属性
feign:
  hystrix:
    enabled: true
2.主启动添加注解@EnableHystrix
```

**Controller中修改代码**

```java
@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
})
@GetMapping("/consumer/hystrix/timeout/{id}")
public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
    String result = paymentHystrixService.paymentInfo_TimeOut(id);
    return result;
}

public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
    return "我是消费者80，对方支付系统繁忙，请10s中在试试,/(ㄒoㄒ)/~~";
}
```

**测试结果**

![image-20220407201259092](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220407201259092.png)

#### 7. 避免每个业务方法对应一个降级方法(代码膨胀)

```java
除了特殊的核心业务有专属的fallback，其他普通的可以通过@DefaultProperties(defaultFallback =  "")统一跳转到统一结果处理页面
```

```java
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
@Slf4j
@RestController
public class OrderHystrixController {
    ...
    //    global fallback(全局服务降级)
    public String payment_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后再试试，/(ㄒoㄒ)/~~";
    }
    ...
}
```

**测试结果**

![image-20220409123804903](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220409123804903.png)

#### 8. 解决：通配服务降级FeginFallBack(和业务逻辑混在一起?)

```java
根据cloud-consumer-fegin-hystrix-order80已经有的PaymentHystrixService接口，重新新建一个类（PaymentFallbackService）实现接口里面的方法进行异常处理
```

```java
public class PaymentFallbackService implements PaymentHystrixService{

    @Override
    public String paymentInfo_OK(Integer id) {
        return "--------PaymentFallbackService fall back(paymentInfo_OK)/(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "--------PaymentFallbackService fall back(paymentInfo_TimeOut)/(ㄒoㄒ)/~~";
    }
}
```

**全局服务降级**

```java
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallbackService.class) //统一交给PaymentHystrixService
public interface PaymentHystrixService {...}
```

**把8001微服务提供者stop，测试结果**

```java
此时服务端provider已经宕机，但是我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息，而不会耗死服务器
```

![image-20220409125537209](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220409125537209.png)

#### 9. Hystrix服务熔断

```java
熔断机制是应对微服务雪崩效应的一种微服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进行熔断该节点微服务的调用，快速返回错误的响应信息。
    当检测到该节点微服务调用响应正常后，恢复调用链路
```

**8001PaymentService编写代码**

![image-20220409135142470](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220409135142470.png)

```java
//    服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    }) //失败率达到多少后熔断
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*********id 不能为负数");
        }\ = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功, 流水号: " + serialNumber;
    }
    
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能为负数，请稍后再试，/(ㄒoㄒ)/~~============>id: " + id;
    }
```

```java
tips: 在大量的失败请求后，发送一次正确的请求并不会立即返回成功的结果，会等正确率上升了才会发送正确结果 (当前代码)10次请求6次失败-----服务熔断-----慢慢恢复调用链路
```

#### 10. Hystrix服务监控web界面

![image-20220409161619694](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220409161619694.png)

**主启动类**

```java
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardMain9001 {

    public static void main(String[] args) {

        SpringApplication.run(HystrixDashboardMain9001.class, args);
    }
}
```

![image-20220409162015598](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220409162015598.png)

```java
1.8001主启动类中注册bean
/**
     * 此配置为了服务监控，与服务器本身无关，springcloud升级后
     * ServletRegistrationBean因为升级SpringBoot的默认路径不是/hystrix.stream
     * 在项目里面配置下面的servlet
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
```

**测试结果**

![image-20220409163007275](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220409163007275.png)

```java
监控地址:http://localhost:8001/hystrix.stream
```

### 五 服务网关Gateway 

```java
1.springcloud Gateway 使用的webflunx + reactor-netty响应式编程(底层使用Netty通讯框架)异步非阻塞IO
```

#### 1. Route(路由)

```
路由时构建网关的基本模块，它由ID，目标URL，一系列的断言和过滤器组成，如果断言为true则匹配该路由
```

**Gateway9527搭建**

```java
1.新建moudle---cloud-gateway-gateway9527

2.写pom
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
<!--        eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fqh.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

3.写yml    
server:
  port: 9527

spring:
  application:
    name: cloud-gateway
    
eureka:
  client:
    # 表示自己是否注册进eurekaServer默认true
    register-with-eureka: true
    # 是否从EurekaServer抓取已有的信息，默认为true, 集群必须设置为true才能配置ribbon使用负载均衡
    fetch-registry: true
    service-url:
      #      集群
      #      defaultZone: http://eureka7001.com:7001/eureka,http://eureka/7002.com:7002/eureka #集群
      #      单机
      defaultZone: http://eureka7001.com:7001/eureka
  instance:
    instance-id: cloud-gateway-service
        
4.主启动类
@EnableEurekaClient
@SpringBootApplication
public class GateWayMain9527 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayMain9527.class, args);
    }
}
5.不想暴露8001端口，希望在外面挡一个9527------配置yml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      routes:
        - id: payment_routh           #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: http://localhost:8001  #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**    #断言，路径想匹配的进行路由
        - id: payment_routh2
          uri: http://localhost:8001
          predicates:
            - Path=/payment/lb/**

```

**测试**

![image-20220410143519150](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220410143519150.png)

**配置方式2(注册RouteLocator的Bean)**

```java
1.通过9527网关访问到外网的百度新闻网址
```

```java
@Configuration //配置类
public class GateWayConfig {

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        
//        http://news.baidu.com/guonei
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_fqh1", r -> r.path("/guonei")
                .uri("http://news.baidu.com/guonei"))
                    .build();
        return routes.build();
    }
}

```

**测试**

![image-20220410145012117](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220410145012117.png)

**配置动态路由**

```java
默认情况下Gateway会根据注册中心注册的服务列表，以注册中心微服务名为路径创建动态路由进行转发，从而实现动态路由的功能
1.启动一个注册中心，7001，两个微服务提供者8001, 8002
    
2.在9527yml中添加配置
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes:
        - id: payment_routh           #路由的ID，没有固定规则但要求唯一，建议配合服务名
#          uri: http://localhost:8001  #匹配后提供服务的路由地址
          uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**    #断言，路径想匹配的进行路由
        - id: payment_routh2
#          uri: http://localhost:8001
          uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/**

```

**测试动态路由**

![image-20220410150718788](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220410150718788.png)

#### 2. Predicate(断言)

```
匹配HTTP请求中的所有内容（例如请求头参数），如果请求与断言相匹配则进行路由
```

![image-20220411133727033](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220411133727033.png)

```java
predicates:
            - Path=/payment/lb/**
            #在什么时候生效**/
            - After=2022-04-11T14:42:29.435+08:00[Asia/Shanghai]
			- Before=....
            - Between=....
            - Cookie=username,fqh //携带cookie
            - Header=X-Request-Id, \d+ //请求头含有x-Request-Id,值为整数
       		- Host="www.fqh.com"
            - Method="GET" 
            - Query=username, \d+ //参数username为正整数
```

**curl微服务测试**

```java
C:\Users\FQH>curl http://localhost:9527/payment/lb --cookie "username=fqh"
8002
C:\Users\FQH>curl http://localhost:9527/payment/lb --cookie "username=fqh"
8001
C:\Users\FQH>curl http://localhost:9527/payment/lb --cookie "username=fqh"
8002
C:\Users\FQH>curl http://localhost:9527/payment/lb --cookie "username=fqh"
8001
C:\Users\FQH>curl http://localhost:9527/payment/lb --cookie "username=fqh"
8002
C:\Users\FQH>curl http://localhost:9527/payment/lb --cookie "username=fqh"
8001
C:\Users\FQH>

```

**Header**

```java
C:\Users\FQH>curl http://localhost:9527/payment/lb -H "X-Request-Id:123"
8002
C:\Users\FQH>
C:\Users\FQH>curl http://localhost:9527/payment/lb -H "X-Request-Id:123"
8001
C:\Users\FQH>curl http://localhost:9527/payment/lb -H "X-Request-Id:123"
8002
C:\Users\FQH>curl http://localhost:9527/payment/lb -H "X-Request-Id:123"
8001
C:\Users\FQH>
```

#### 3. Filter(过滤器)

```
使用过滤器，可以在请求被路由前或者之后对请求进行修改
```

**GatewayFilter**

**GlobalFilter(实现GlobalFilter和Ordered)**

```java
@Slf4j
@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("**************进入 MyLogGateWayFilter： " + new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null) {
            log.info("***********用户名为null, 非法，/(ㄒoㄒ)/~~");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
```

### 六 分布式的配置中心(SpringCloud Config)

```
1.集中管理配置文件
2.不同环境不同配置，动态化的配置更新，分环境部署比如dev/prod/beta/release
3.运行期间动态调整配置，不在需要在每个服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自动的信息
4.当配置发生变动时，服务不需要重启既可感知到配置的变化并应用新的配置
5.将配置信息以REST接口的形式暴露
```

#### 1. GitHub整合配置

```java
1.github新建一个repository（springcloud-config）
2.克隆到本地{git clone git@github.com:monkey1ew/springcloud-config.git}
```

![image-20220411150628686](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220411150628686.png)

```java
3.新建模块cloud-config-center-3344

4.写pom
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>
    
5.写yml
server:
  port: 3344

spring:
  application:
    name: cloud-config-center #注册进Eureke的服务名称
  cloud:
    config:
      server:
        git:
          uri: git@github.com:monkey1ew/springcloud-config.git #git仓库名字
          # 搜索目录
          search-paths:
            - springcloud-config
      # 读取分支
      label: master

#服务注册进eureka地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka

6.主启动类
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigCenterMain3344 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}

7.windows的host文件增加映射
127.0.0.1 config-3344.com
```

![image-20220411151936964](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220411151936964.png)

```java
8.测试通过Config微服务是否可以从github上面获取内容
```

![image-20220411164746590](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220411164746590.png)

```java
*****************************************************************
```

**新建客户端cloud-config-client-3355**

```java
1.写pom
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>

*******************
2.新建bootstrao.yml (系统级的,优先级更高)
server:
  port: 3355

spring:
  application:
    name: config-client
  cloud:
    config:
      label: master #分支名称
      name: config  #配置文件名称
      profile: dev  #读取后缀名称
      uri: http://localhost:3344 #配置中心地址
eureka:
  client:
    service-url: http://localhost:7001/eureka

```

**Controller代码**

```java
@RestController
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}
```

**测试结果(通过从3344上读取的配置)**

![image-20220411164718492](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220411164718492.png)

#### 2. 分布式的动态刷新问题

```java
1.对Github上面的配置内容修改
2.刷新3344，发现修改的内容立即响应
3.刷新3355，发现内容还是原来的(×)
```

**客户端的动态刷新**

```java
1.客户端的yml中配置
# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: "*"
2.Controller上面添加@RefreshScope注解
```

### 七 SpringCloud Bus 消息总线

#### 1. SpringCloud Bus动态刷新全局广播

```java
1.新建模块cloud-config-client-3366
-------------------------2.写pom
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>
-------------------------3.写yml
server:
  port: 3366

spring:
  application:
    name: config-client
  cloud:
    config:
      label: master #分支名称
      name: config  #配置文件名称
      profile: dev  #读取后缀名称
      uri: http://localhost:3344 #配置中心地址


eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka

# 暴露监控端点
management:
  endpoint:
    web:
      exposure:
        include: "*"
----------------------4.主启动类
@EnableEurekaClient
@SpringBootApplication
public class ConfigClientMain3366 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3366.class, args);
    }
}
---------------------5.Controller
@RefreshScope
@RestController
public class ConfigClientController {

    @Value("{server.port}")
    private String serverPort;

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return "serverPort: " + serverPort + "\t\n\n configInfo: " + configInfo;
    }
}
```

**设计思想**

**1.利用消息总线触发一个客户端/bus/refresh，而刷新所有的客户端的配置
    不可取：打破了微服务的职责单一性，因为微服务本身是业务模块，它不应该承担配置刷新的职责**

**2.利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有的客户**

```java
--------------1.3344配置服务中心pom添加依赖
<!--        添加消息总线RabbitMq支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>

--------------2.3344的yml中新增rabbitmq配置
#rabbitmq配置
  rabbitmq:
    port: 5672
    host: 192.168.159.138
    username: admin
    password: 123
    
#rabbitmq相关配置, 暴露bus刷新配置的端点
management:
  endpoint:
    web:
      exposure:
        include: 'bus-refresh'

--------------3.3355客户端pom添加依赖
<!--        添加消息总线RabbitMq支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
        
--------------4.3355客户端的yml配置
rabbitmq:
    host: 192.168.159.138
    port: 5672
    username: admin
    password: 123
    
--------------5.配置3366客户端（同3355一样）
```

**发送POST请求**

```java
curl -X POST "http://localhost:3344/actuator/bus-refresh"
```

![image-20220413174452357](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220413174452357.png)

```java
主题topic：发布订阅模式，客户端3355和3366都能获取到3344服务端更新的配置
    
```

**测试效果**

![image-20220414121816234](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414121816234.png)



#### 2. SpringCloud Bus动态刷新定点通知

```java
curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355" //指定client
```

### 八 SpringCloud Stream 消息驱动

```java
1.屏蔽底层消息中间件的差异，降低切换成本，统一消息编程模型
```

#### 1. Stream的设计思想

```java
中间件的差异性导致我们实际项目开发给我们造成了一定的困扰，消息队列之间的适配非常麻烦，造成系统耦合性太高，SpringCloud Stream给我们提供了一种解耦合的方式
```

**Binder**

![image-20220414195628928](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414195628928.png)

**Stream中的消息通信方式遵循发布—订阅模式(topic主题)**

![image-20220414200005210](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414200005210.png)

#### 2. Stream编码常用注解

```java
1.Binder:连接中间件，屏蔽差异
2.Channel:信道
3.Source和Sink:消息生产者和消费者
```

**@Middleware**

```java
指定消息中间件
```

**Binder**



**@Input**



**@Output**



**@StreamListener**

```java
消息监听者
```

**@EnableBinding**

```java
指信道channel和exchange交换机绑定在一起
```



#### 3. 消息驱动之生产者

```java
-------------------1.新建module: cloud-stream-rabbitmq-provider8801
        
```

**编写pom文件**

```java
-------------------2.写pom.xml
<!--        stream和rabbitmq-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
<!--        web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
<!--        基础配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>

```

**编写yml文件**

```java
--------------------3.写yml
server:
  port: 8801

spring:
  application:
    name: cloud-stream-provider

  cloud:
    stream:
      binders: # 配置要绑定的rabbitmq的服务信息
        defaultRabbit: #定义的名称，用于binding整合
          type: rabbit #消息组件类型
          environment: #设置rabbitmq的相关环境配置
            spring:
                rabbitmq:
                  host: 192.168.159.138
                  port: 5672
                  username: admin
                  password: guest
      bindings: #服务的整合处理
        output: #信道的名称
          destination: studyExchange #使用的交换机名称
          content-type: application/json #设置消息类型为json，文本text/plain
          default-binder: defaultRabbit #设置要绑定得消息服务具体设置
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7002/eureka
  instance:
    lease-renewal-interval-in-seconds: 2 #设置心跳得时间间隔(默认30s)
    lease-expiration-duration-in-seconds: 5 #如果超过了5s时间间隔
    instance-id: send-8801.com #信息列表显示主机名称
    prefer-ip-address: true #访问路径变为ip显示
```

**主启动类**

```java
@EnableEurekaClient
@SpringBootApplication
public class StreamMQMain8801 {

    public static void main(String[] args) {

        SpringApplication.run(StreamMQMain8801.class, args);
    }
}

```

**业务类**

```java
@EnableBinding(Source.class) //定义消息的推送管道
public class IMessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output; //消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("**************发送serial: " + serial);
        return null;
    }
}

```

![image-20220414203556799](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414203556799.png)

```java
@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider messageProvider;

    @GetMapping(value = "/sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }
}

```

**测试效果**

![image-20220414210141264](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414210141264.png)

#### 4. 消息驱动之消费者

```java
-------------1.新建cloud-stream-rabbitmqc-onsumer8802
					cloud-stream-rabbitmqc-onsumer8803模块
    
-------------2.pom.xml同8801一样
```

**yml配置**

```java
server:
  port: 8802

spring:
  application:
    name: cloud-stream-consumer

  cloud:
    stream:
      binders: # 配置要绑定的rabbitmq的服务信息
        defaultRabbit: #定义的名称，用于binding整合
          type: rabbit #消息组件类型
          environment: #设置rabbitmq的相关环境配置
            spring:
              rabbitmq:
                host: 192.168.159.138
                port: 5672
                username: admin
                password: 123
      bindings: #服务的整合处理
        input: #信道的名称---输入
          destination: studyExchange #使用的交换机名称
          content-type: application/json #设置消息类型为json，文本text/plain
          binder: defaultRabbit #设置要绑定得消息服务具体设置
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka
  instance:
    lease-renewal-interval-in-seconds: 2 #设置心跳得时间间隔(默认30s)
    lease-expiration-duration-in-seconds: 5 #如果超过了5s时间间隔
    instance-id: recevie-8802.com
    prefer-ip-address: true
```

**主启动类**

```java
@SpringBootApplication
public class StreamMQMain8802 {

    public static void main(String[] args) {

        SpringApplication.run(StreamMQMain8802.class, args);
    }
}
```

**业务类**

```java
@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {

        System.out.println("消费者1号---------------->收到消息: "
                + message.getPayload() + "\t port: " + serverPort);
    }
}
```

**测试效果**

```java
8801发送------8802接收
```

**Rabbitmq的Exchange图**

![image-20220414211658031](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414211658031.png)

**8801的控制台**

![image-20220414211726715](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414211726715.png)

**8802的控制台**

![image-20220414211740970](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220414211740970.png)

#### 5. 分组消费与消息持久化

```java
1.按照8802，clone出来一份运行8803模块
```

**运行后存在的两个问题**

**消费**

```java
目前8802/8803 都收到了8801发送的消息(消息被重复消费)
```

**分组**

```java
原理:	微服务应用放置于同一个group，就能够保证消息只会被其中一个应用消费一次
    不同的组是可以消费的，同一个组内会发生竞争关系，只有其中一个可以消费
    
	Stream中处于同一个group中的多个消费者是竞争关系，就能保证消息只会被其中一个应用消费一次，不同组是可以全面消费的（重复消费）
```

![image-20220415122719855](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415122719855.png)

```java
Queue studyExchange.anonymous.apvbjgWXT9-fml5UGUXO8w 8802
Queue studyExchange.anonymous.nU3lDLbVTZi1eR3VmoRHrQ 8803
```

**解决重复消费问题**

```java
1.group: fqhA，fqhB
2.修改8802和8803的yml
```

![image-20220415123345543](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415123345543.png)

**发现组名已经改变**

![image-20220415123451084](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415123451084.png)

```java
测试发送两条：8802(groupA)和8803(groupB)都能收到2条消息(不同的group)
    		8802(groupA)和8803(groupA)只能一个消费消息（采用轮询的方式消费）(相同的group)
```

**效果图**

```java
tips: 两个consumer................
```

![image-20220415124100171](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415124100171.png)

**生产者发送2条消息**

![image-20220415123917193](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415123917193.png)

**8802接收到1条**

![image-20220415123855911](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415123855911.png)

**8803接收到1条**

![image-20220415124008026](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415124008026.png)

**持久化**

```java
1.停止8802/8803并去除掉8802的分组group: fqhA
```

**8801先发送4条消息到rabbitmq**

![image-20220415184243925](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415184243925.png)

**启动8802(无分组属性配置)，后台没有打印MQ消息**

![image-20220415184313846](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415184313846.png)

```java
消息丢失了？？？？
```

**启动8803(有分组属性), 后台打印出MQ的消息**

![image-20220415184335978](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415184335978.png)

### 九 SpringCloud Sleuth 分布式请求链路追踪

**微服务架构产生问题**

```java
在微服务框架中，一个客户端发起的请求在后端系统中会经过多个不同的服务节点调用来协同产生最后的请求结果，每一个前端请求都会形成一条复杂的分布式服务调用链路，链路中的任何一个环节出现高延迟或错误都会引起整个请求最后的失败
```

#### 1. ZipKin

```java
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

![image-20220415190701109](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415190701109.png)

#### 2. 服务提供者

```java
1.引入pom依赖
		<!--        包含了sleuth + zipkin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
2.增加yml配置
spring:
  application:
    name: cloud-payment-service
  zipkin:
    base-url: http://localhost:9411
    sleuth:
      sampler:
        #采样率值介于 0-1之间，1-全部采集
        probability: 1

```

**Controller新增**

```java
@GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "hi, i am paymentzipkin server fall back, welcome to fqh------hhhhhhh.....";
    }
```

#### 3. 服务消费者

```java
改动的pom和yml同8001生产者一样
```

![image-20220415192215943](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415192215943.png)

**Controller新增**

```java
//    zipkin + sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin", String.class);
        return result;
    }
```

```java
依次启动eureka7001/8001/80
```

#### 4. 打开浏览器访问http://localhost:9411

![image-20220415201104807](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220415201104807.png)



## Ⅲ 高级部分🙀

### 十 SpringCloud Alibaba入门

![image-20220416164556634](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416164556634.png)

```java
1.服务的降级限流
2.服务注册与发现
3.分布式配置管理
4.消息驱动
5.阿里云对象存储
6.分布式任务调度
```

![image-20220416165249638](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416165249638.png)

**引入gav坐标**

```java
			<!--spring cloud 阿里巴巴-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
```

### 十一 Nacos服务注册与配置中心

```java
一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
    Nacos = Eureka + Config + Bus
```

#### 1. Nacos下载和安装

**下载并运行nacos**

![image-20220416171843853](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416171843853.png)

**访问: http://100.100.75.0:8848/nacos/index.html**

![image-20220416172202222](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416172202222.png)

#### 2. Nacos之服务提供者注册

```java
1.新建cloudalibaba-provider-payment9001模块
```

**引入gav坐标pom依赖**

```java
 <dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 </dependency>
```

**编写yml配置文件**

```java
 //配置文件中配置 Nacos Server 地址
server:
  port: 9001

spring:
  application:
    name: nacos-payment-provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #配置nacos地址

management:
  endpoint:
    web:
      exposure:
        include: "*"
```

**编写主启动类**

```java
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain9001 {

    public static void main(String[] args) {
        
        SpringApplication.run(PaymentMain9001.class, args);
    }
}
```

**Controller代码**

```java
@RestController
public class PaymentController {
    
    @Value("${server.port}")
    private String serverPort;
    
    @GetMapping(value = "/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id) {
        return "nacos registry, serverPort: " + serverPort + "\t id: " + id;
    }
}

```

**nacos中查看服务**

![image-20220416182411362](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416182411362.png)

**演示自带的负载均衡**

```java
1.新建cloudalibaba-provider-payment9002模块 跟9001一样
```

**9002微服务提供者的注册**

![image-20220416183153049](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416183153049.png)

#### 3. Nacos之服务消费者注册

```java
1.新建cloudalibaba-consumer-nacos-order83模块
```

**引入nacos的pom坐标**

```java
<dependencies>
    <!--        nacos的依赖-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.fqh.springcloud</groupId>
        <artifactId>cloud-api-common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

**集成了netfilx的ribbon**

![image-20220416184504059](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416184504059.png)

**编写yml配置**

```java
server:
  port: 83

spring:
  application:
    name: nacos-order-consumer
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #配置nacos地址

#消费者将要去访问的微服务名称(注册成功jinnacos的服务提供者)
server-url:
    nacos-user-service: http://nacos-payment-provider
```

**Controller代码**

```java
@Slf4j
@RestController
public class OrderNacosController {
    
    @Resource
    private RestTemplate restTemplate;
    
    @Value("${server-url.nacos-user-service}")
    private String serverURL;
    
    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Long id) {
        return restTemplate.getForObject(serverURL + "/payment/nacos/" + id, String.class);
    }
}
```

![image-20220416185346104](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416185346104.png)

**测试**

```java
返回9001
```

![image-20220416185815880](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416185815880.png)

```java
返回9002
```

![image-20220416185839507](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416185839507.png)

#### 4. 服务注册中心对比

```java
Eureka		AP
Zookeeper 	CP
Consul		CP
Nacos		AP + CP
```

![image-20220416190348759](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220416190348759.png)

**Nacos支持AP和CP模式切换**

```java
1.如果不需要存储服务级别的信息且服务实例时通过Nacos-client注册，并且能够保持心跳上报---------选择AP
2.如果需要服务级别或者存储配置信息---------必须CP
```

**模式切换**

```java
curl -X PUT '$NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP'
```

#### 5. 服务配置中心之基础配置

```java
1.新建cloudalibaba-config-nacos-client3377模块
2.引入pom依赖
  	<!--  nacos-config -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-nacos-config</artifactId>
        </dependency>
        <!--        nacos的依赖-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.fqh.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

```



```java
Nacos同SpringCloud-config一样,在项目初始化时,要保证先从配置中心拉取
拉取配置之后,才能保证项目的正常启动

springboot配置文件的加载存在优先级
		bootstrap优先级高于application
```

**编写application.yml**

```java
spring:
  profiles:
    active: dev #开发环境
```

**编写bootstrap.yml**

```java
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #nacos服务注册中心地址
      config:
        server-addr: localhost:8848 #nacos作为配置中心地址
        file-extension: yaml #指定yaml格式的配置
```

**主启动类**

```java
@EnableDiscoveryClient
@SpringBootApplication
public class NacosConfigClientMain3377 {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigClientMain3377.class, args);
    }
}

```

**Controller代码**

```java
@RefreshScope //支持Nacos的动态刷新功能
@RestController
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
```

**在Nacos中添加配置信息**

```java
${prefix} - ${spring.profiles.active} . ${file-extension}

prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。

spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档

注意，当 active profile 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}

file-extension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension来配置。 目前只支持 properties 类型。

group
group 默认为 DEFAULT_GROUP，可以通过 spring.cloud.nacos.config.group 配置。
```

![image-20220417212556059](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220417212556059.png)

**测试结果**

![image-20220417212650646](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220417212650646.png)

#### 6. 服务配置中心之分类配置

```java
抛出问题:
	多环境[dev,test,prod]
	微服务下面的子项目环境.......
```

![image-20220417213205610](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220417213205610.png)

#### 7. Nacos之命名空间分组和DataID

```java
指定spring.profile.active和配置文件的DataID来使不同的环境读取不同的配置文件
```

![image-20220418154420476](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418154420476.png)

**修改application.yml的profile**

```java
spring:
  profiles:
    active: test
```

![image-20220418155007968](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418155007968.png)

```java
-------------------测试成功发现内容已经改变
```

#### 8. Nacos的分组配置方案

**新建TEST_GROUP和DEV_GROUP组**

![image-20220418155543093](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418155543093.png)

```java
1.application.yml
spring:
  profiles:
    active: info
2.bootstrap.yml
spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #nacos服务注册中心地址
      config:
        server-addr: localhost:8848 #nacos作为配置中心地址
        file-extension: yaml #指定yaml格式的配置
        group: TEST_GROUP //分组
```

**测试**

![image-20220418155737897](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418155737897.png)

```java
nacos-config-client-info.yaml---->来自DEV_GROUP
```

#### 9. Nacos的NameSpace配置

```java
1.新建dev/test命名空间
```

![image-20220418155951171](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418155951171.png)

```
1.application.yml
active: dev

2.bootstarp.yml中添加namespace配置
config:
        server-addr: localhost:8848 #nacos作为配置中心地址
        file-extension: yaml #指定yaml格式的配置
        group: DEV_GROUP #分组
        namespace: bed7f770-0764-4a7f-800e-c20dab8c7f30 #复制Nacos上面的命名空间ID
```

**相同的namespace读取不同组的配置**

![image-20220418160924496](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418160924496.png)

```java
bed7f770-0764-4a7f-800e-c20dab8c7f30, DEV_namespace-->TEST_GROUP...
```

### 十二 Nacos集群和持久化配置

#### 1. 切换Nacos自带数据库derby

```java
1.在nacos\conf\目录下找到nacos-mysql.sql文件并执行
```

![image-20220418172003284](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418172003284.png)

```java
2.nacos\conf\目录下找到application.properties
==========添加==================
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf-8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=fqh
```

**换过数据库后发现数据没了**

![image-20220418172627697](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418172627697.png)

**添加一个配置到数据库里面去了**

![image-20220418172852159](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418172852159.png)

#### 2. Linux版Nacos + MySQL生产环境配置

```java
1个Nginx + 3个Nacos注册中心 + 1个Mysql
```

**同样修改application.properties配置**



**Linux服务器上nacos的集群配置cluster.conf**

![image-20220418204216460](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418204216460.png)

**编辑Nacos的startup.sh文件**

```java
step1:
	while getopts ":m:f:s:p" opt {这里p要加上,不然找不到参数}
```

![image-20220418204711526](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418204711526.png)

```java
step2
```

![image-20220418205129726](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220418205129726.png)

**启动Nacos集群**

```java
./startup.sh -p 3333...4444...5555
```

![image-20220420163017698](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420163017698.png)

**Nginx的配置（负载均衡）**

![image-20220420163636327](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420163636327.png)

**启动Nginx**

```java
./nginx -c /usr/local/nginx/conf/nginx.conf

```

![image-20220420164844563](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420164844563.png)

**虚拟机启动nacos集群JVM内存不够（失败）**



### 十三 Sentinel服务限流

**Hystrix存在的问题**

```java
1.需要我们自己手工搭建监控平台
2.流控，速率控制，服务熔断，服务降级
```

#### 1. 下载Sentinel的仪表盘并运行

![image-20220420180445830](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420180445830.png)

#### 2. 初始化工程

**新建cloudalibaba-sentinel-service8401模块**

```java
1.引入pom依赖
<dependencies>
        <!-- nacos       -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!--  sentinel-datasource-nacos 持久化用到  -->
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
        <!--  sentinel      -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <!-- openfegin       -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!--        springboot web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.fqh.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

**编写application.yml**

```java
server:
  port: 8401

spring:
  application:
    name: cloudalibaba-sentinel-service
  cloud:
    nacos:
      discovery:
        #Nacos服务注册中心地址
        server-addr: localhost:8848
     sentinel:
       transport:
         #配置Sentinel Dashboard地址
         dashboard: localhost:8080
         #默认8719端口,假如被占用会自动从8719开始依次+1扫描,直至找未被占用的端口
         port: 8719

management:
  endpoints:
    web:
      exposure:
        include: '*'
```

**主启动类和流控Controller**

![image-20220420182716450](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420182716450.png)

![image-20220420182831830](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420182831830.png)

**后台查看**

```java
tips: 
	Sentinel采用的是懒加载机制，执行一次http://localhost:8401/testA
	
```

![image-20220420183441651](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420183441651.png)

#### 3. Sentinel流控规则

![image-20220420184650238](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420184650238.png)

**流控模式**

```java
1.直接
2.关联
3.链路
```

#### 4. Sentinel流控QPS直接失败

**配置testA流控**

```java
QPS: 配置限制1次/s
```

![image-20220420185101009](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420185101009.png)

**测试结果**

![](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220420184936458.png)

**我们需要一个备用方法**

#### 5. Sentinel流控线程数直接失败

```java
线程数: 当调用该api的线程数时完成限流
```

#### 6. 流控模式之关联

```java
tips:
	当关联的资源达到阈值时，就限流自己
```

![image-20220421131425902](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421131425902.png)

**POSTMAN模拟并发请求/testB**

![image-20220421132215160](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421132215160.png)

**测试发现A被限流了**

![image-20220421132143503](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421132143503.png)

#### 7. 流控效果之预热

```java
系统初始化的阈值为10 / 3 === 3，刚开始阈值为3，然后过了5s后阈值才慢慢升高恢复到10(设定的值)
```

![](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421133057041.png)

**配置**

![image-20220421133216648](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421133216648.png)

### 十四 Sentinel服务熔断降级

```java
RT: (平均响应时间，秒级)
	平均响应时间 超出阈值且在时间窗口内通过的需求>=5，两个条件同时满足后触发降级
    窗口期过后关闭断路器
    RT最大4900 (通过配置-Desp.sentinel.static.max.rt=XXX)

异常比例:(秒级)
    QPS >= 4 且异常比例 (秒级统计) 超过阈值时，触发降级，时间窗口结束后，关闭降级
```

![image-20220421135828112](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421135828112.png)

**Sentinel的熔断没有半开状态**

```java
tips:
	半开的状态系统自定去检测是否请求异常，没有异常就关闭断路器恢复使用，
	有异常则继续打开断路器，造成不可用
```

#### 1. 降级—RT

**sentinel配置**

![image-20220421141622624](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421141622624.png)

```java

```

#### 2. 降级—异常比例

![image-20220421145751505](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421145751505.png)

```java
tips:
	请求错误率超过20%(比例阈值0.2)的时候服务熔断 && 每秒请求数 > 5(最小请求数)
        熔断时长5s，5s内请求的服务仍然是熔断状态，5s后恢复正常调用
```

**测试界面**

```java
使用Jemeter: 1s发送10个请求
```

![](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421145055064.png)

#### 3. 降级—异常数

```java
当资源近1分钟的异常数目超过阈值之后进行熔断，注意统计时间窗口是分钟级别的，
若timewindow小于60%，则熔断状态结束后还可能进入熔断状态
```

#### 4. Sentinel热点key（上）

![image-20220421150815068](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421150815068.png)

**@SentinelResource注解—>豪猪哥的@HystrixCommand注解**

**Controller代码**

```java
@SentinelResource参数:
	value: 资源名
	blockHandler: 兜底的方法


@SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    @GetMapping("/testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "--------------testHotKey";
    }
    
    public String deal_testHotKey(String p1, String p2, BlockException blockException) {
//        sentinel的默认提示: Blocked by sentinel limiting...
        return "--------------testHotKey, deal_HotKey/(ㄒoㄒ)/~~";
    }
```

**界面配置**

![image-20220421152948002](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421152948002.png)

```java
tips: 参数索引位置为0的，限制每秒访问的次数
```

**测试结果**

![image-20220421153002858](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421153002858.png)

#### 5. Sentinel热点key（下）

**参数例外项**

```java
tips: 动态的增加参数的限流值
    这里配置的指: 当p1参数==5时，限流阈值可以到200才进行限流
```

![image-20220421153709541](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421153709541.png)

**测试后**

```java
无论刷多少次，都没有进行限流 QPS<200
```

![image-20220421153844920](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421153844920.png)

**@SentinelResource注解参数—>fallback.......**

![image-20220421154236395](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220421154236395.png)

#### 6. Sentinel系统自适应限流

![image-20220422171202023](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422171202023.png)

#### 7. @SentinelResource注解配置

**按资源名称限流—cloudalibaba-sentinel-service8401模块**

1. 新增业务类RateLimitController

   ![image-20220422172259724](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422172259724.png)

2. 页面配置

   ![image-20220422172429102](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422172429102.png)

3. 测试效果

   ![image-20220422172524081](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422172524081.png)

4. QPS超过1s一个

   ![image-20220422172709846](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422172709846.png)

5. 关闭服务8401，发现流控规则消失

**按照url进行限流**

![image-20220422173142642](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422173142642.png)

1. 页面配置

   ![image-20220422173322489](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422173322489.png)

2. 测试效果

   ![image-20220422173357918](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422173357918.png)

```java
总结:
	1.系统默认的，没有体现我们自己的业务要求
	2.我们自定义的方法跟业务代码耦合
	3.每一个业务方法都添加了一个备用方法，代码膨胀
	4.全局统一的处理方法没有体现
```

#### 8. 自定义限流处理逻辑类

![image-20220422180705069](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422180705069.png)

**Controller代码修改指定为自定义**

![image-20220422180829699](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422180829699.png)

**测试效果**

![image-20220422181028268](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220422181028268.png)

#### 9. Sentinel服务熔断

**只配置fallback**

![image-20220423161932227](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423161932227.png)

页面效果

![image-20220423162003261](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423162003261.png)

![image-20220423162022224](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423162022224.png)

**只配置blockHandler**

![image-20220423162857513](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423162857513.png)

页面配置和效果

![image-20220423162941411](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423162941411.png)

![image-20220423162920464](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423162920464.png)

**两种都配置的效果**

![image-20220423164327752](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423164327752.png)

![image-20220423164346327](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423164346327.png)

```java
1.请求正确的id值但是QPS > 1，会被我们配置的blockHandler限流
2.请求错误的id值但是QPS <= 1，会被我们配置的fallback处理java异常
3.如果又请求错误的id，QPS又 > 1，blockHandler优先执行
```

**ribbon + OpenFegin + fallback**

1. 激活sentinel对feign的支持

   <img src="C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423165355322.png" alt="image-20220423165355322" style="zoom:100%;" />

2. 主启动类添加支持feign注解

   ![image-20220423165544543](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423165544543.png)

3. 业务类

   ![image-20220423170814655](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423170814655.png)

4. feign的服务降级类

   ![image-20220423170858698](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423170858698.png)

5. Controller调用Servcie

   ![image-20220423170930539](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423170930539.png)

6. 关闭9003和9004微服务提供者

   ![image-20220423170959769](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423170959769.png)

#### 10. Sentinel规则持久化

```java
将限流配置规则持久化到Nacos保存，只要刷新8401某个rest地址，sentinel控制台的流控规则就能看到，只有Nacos里面的配置不删除，针对8401上sentinel的流控规则持续有效
```

1. pom中添加依赖

   ![image-20220423172019034](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423172019034.png)

2. yml中新增配置

   ![image-20220423172353528](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423172353528.png)

3. 添加Nacos业务规则配置

   ![image-20220423204932540](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220423204932540.png)

   ```java
   resource: 资源名称
   limitApp: 来源App
   grade: 阈值类型，0表示线程数，1表示QPS
   count: 单机阈值
   strategy: 流控模式，0表示直接，1表示关联，2表示链路
   clusterMode: 是否集群
   ```

4. 启动8401刷新sentinel的流控规则



### 十五 Seata处理分布式事务

#### 1. 分布式事务的问题

```java
全局多数据源事务的数据一致性问题
```

#### 2. Seata简介

```java
Seata 是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务。
```

#### 3. Seata术语

#### TC (Transaction Coordinator) - 事务协调者

维护全局和分支事务的状态，驱动全局事务提交或回滚。

#### TM (Transaction Manager) - 事务管理器

定义全局事务的范围：开始全局事务、提交或回滚全局事务。

#### RM (Resource Manager) - 资源管理器

管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。

![img](https://img2022.cnblogs.com/blog/2757442/202203/2757442-20220306100422681-639594187.png)

#### 4. 配置Seata

1. 修改file.conf文件【自定义事务组名称 + 事务日志存储模式为db + 数据库连接信息】

   ![image-20220425102206481](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220425102206481.png)

2. 配置数据库连接信息

   ![image-20220425124522991](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220425124522991.png)

3. 修改registry.conf的file为nacos类型

   ![image-20220425124612671](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220425124612671.png)

4. github上下载sql文件，创建相关表

   ![image-20220425124701780](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220425124701780.png)

#### 5. Seata业务数据库准备

```java
创建三个微服务，一个订单服务，一个库存服务，一个账户服务
当用户下单时，会在订单服务中创建一个订单，然后通过rpc调用存储服务扣减下单商品的库存，再通过rpc调用账户服务来扣减用户账户里面的余额，最后在订单服务中修改订单的状态为已完成
```

**该操作跨三个数据库，有两次rpc调用，明显存在分布式事务问题**

![image-20220425135547713](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220425135547713.png)

#### 6. 微服务模块编写

1. 需要引入的pom依赖

   ```java
   <dependencies>
           <!-- seata       -->
           <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
               <exclusions>
                   <exclusion>
                       <groupId>seata-all</groupId>
                       <artifactId>io.seata</artifactId>
                   </exclusion>
               </exclusions>
           </dependency>
           <dependency>
               <groupId>io.seata</groupId>
               <artifactId>seata-all</artifactId>
               <version>1.4.0</version>
           </dependency>
           <!-- nacos       -->
           <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
           </dependency>
           <!--  sentinel      -->
           <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
           </dependency>
           <!-- openfegin       -->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-openfeign</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-actuator</artifactId>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
           </dependency>
           <dependency>
               <groupId>org.mybatis.spring.boot</groupId>
               <artifactId>mybatis-spring-boot-starter</artifactId>
           </dependency>
       </dependencies>
   ```

2. 建立三个微服务模块【account, order, storage】

   ![image-20220427095029102](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095029102.png)

3. 编写yml的配置【微服务名称和服务端口不同】

   ![image-20220427095146839](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095146839.png)

4. 并且修改seata客户端的file.conf

   ![image-20220427095300442](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095300442.png)

5. 在订单微服务中通过【Feign】调用其他微服务

   ![image-20220427095354452](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095354452.png)

   ![image-20220427095416941](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095416941.png)

6. 订单微服务业务代码

   ```java
   @Slf4j
   @Service
   public class OrderServiceImpl implements OrderService {
   
       @Resource
       private OrderDao orderDao;
       @Resource
       private StorageService storageService;
       @Resource
       private AccountService accountService;
   
   
       @Override
       @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
       public void create(Order order) {
           log.info("===============>开始新建订单");
           //1. 新建订单
           orderDao.create(order);
   
           log.info("===============>订单微服务开始调用库存扣减Count");
           //2. 扣减库存
           storageService.decrease(order.getProductId(), order.getCount());
           log.info("===============>订单微服务开始调用库存，做扣减end");
   
           log.info("===============>订单微服务开始调用账户服务扣钱Money");
           //3. 扣用户的钱
           accountService.decrease(order.getUserId(), order.getMoney());
           log.info("==============>订单微服务开始调用账户服务，做扣钱end");
   
           //4. 修改订单的状态 0 -> 1
           log.info("==============>修改订单状态开始");
           orderDao.update(order.getUserId(), 0);
           log.info("=============>修改订单状态结束");
   
           log.info("==========>下单结束");
       }
   
       @Override
       public void update(Long userId, Integer status) {
   
       }
   }
   
   ```

7. Controller的业务代码

   ![image-20220427095635914](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095635914.png)

   ```java
   //正常调用
   http://localhost:2001/order/create?userId=1&productId=1&count=10&money=100
   ```

#### 7. 模拟超时异常【改进】

```java
//Feign的默认超时时长是1s，这里我们改为20s
```

![image-20220427095940570](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427095940570.png)

```java
//异常调用
http://localhost:2001/order/create?userId=1&productId=1&count=10&money=100
//界面返回超时Read TimeOut异常
```

![image-20220427100438917](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427100438917.png)

```java
//分布式事务问题出现
```

**！！！数据库出现问题，account被扣钱，order状态为0，库存被扣**

```java
//解决问题
```

**在调用多个微服务的方法上添加@GlobalTransactional注解**

![image-20220427101823702](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220427101823702.png)

#### 8. Seata原理总结

![Seata AT 模式一阶段](http://c.biancheng.net/uploads/allimg/211210/102A12G8-1.png)

**AT模式【默认】**

```java
//一阶段: 业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。
//二阶段: 提交异步化，非常快速地完成。
//		 回滚通过一阶段的回滚日志进行反向补偿。
```

**一阶段提交执行执行流程，释放本地锁【行锁】和连接资源**

```java
//一阶段，Seata会拦截'业务SQL'
//1.解析SQL语义，找到'业务SQL'更新的业务数据，在业务数据被更新前，将其保存成【Before Image】
//2.执行'业务SQL' 更新业务数据，在业务数据更新之后
//3.将其保存成【After Image】，最后生成【行锁】
以上操作全部在一个数据库事务内完成，保证了一阶段操作的原子性
```

**二阶段事务回滚执行流程**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210117192239542.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80Mzc3NjY1Mg==,size_16,color_FFFFFF,t_70)

```java
//二阶段回滚: Seata需要回滚一阶段已经执行的'业务SQL'，还原业务数据
//采用的是一阶段保存的【Before Image】还原数据，但在还原前首先要进行校验脏写对比【数据库当前业务数据】和 【After Image】，
	tips: 行锁在一阶段事务提交后已经被释放掉，所以在高并发的情况下会存在脏写操作，这里类似【CAS，乐观锁】原理
        //如果两份数据完全一致就说明没有脏写，可以还原业务数据，否则转人工来处理脏写
```

