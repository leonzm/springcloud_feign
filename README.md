# Spring Cloud Feign 笔记

# 简介
> Spring Cloud Feign 基于 Netflix Feign 实现，整合了 Spring Cloud Ribbon 与 Spring Cloud Hystrix，提供了声明式的 Web 服务客户端
定义方式。  在 Spring Cloud Feign 的实现下，只需要创建一个接口并用注解的方式来配置它，即可完成对服务提供方的接口绑定，简化了在使用
Spring Cloud Ribbon 时自行封装服务调用客户端的开发量。

# 使用步骤
* pom 中引入 spring-cloud-starter-eureka 和 spring-cloud-starter-feign 的依赖
* 主类上加 @EnableFeignClients 注解开启 Spring Cloud Feign，也别忘了 @EnableDiscoveryClient 注解
* 定义 XxxService 接口，通过 @FeignClient("[service-instance]") 注解指定服务名来绑定服务，服务名不区分大小写
* 然后在 使用 XxxService 的地方，通过 @Autowired 注解使用
* 配置 application.properties，指定服务注册中心

# 参数绑定
> 在定义各参数绑定时，@RequestParam、@RequestHeader 等可以指定参数名称的注解，它们的 value 不能少。在 Spring MVC 中，这些注解根据参数
名来做默认值，但是在 Feign 中绑定参数必须通过 value 属性来指明具体的参数名，不然会抛出 IllegalStateException 异常，value 属性不能为空

# 继承特性

# Ribbon 配置
* 全局配置，直接使用 ribbon.\<key\>=\<value\>的方式设置 ribbon 的各项默认参数
> 如：ribbon.ConnectTimeout = 500，ribbon.ReadTimeout=5000
* 指定服务配置，采用 \<client\>.ribbon.key=value 的格式进行设置，client 是使用 @FeignClient 注解中的 name 或 value 属性
> 如：HELLO-SERVICE.ribbon.ConnectTimeout=500，
HELLO-SERVICE.ribbon.ReadTimeout=2000，
HELLO-SERVICE.ribbon.OkToRetryOnAllOperations=true，
HELLO-SERVICE.ribbon.MaxAutoRetriesNextServer=2 #最多更换实例次数，
HELLO-SERVICE.ribbon.MaxAutoRetries=1 #每个实例最大重试次数

# Hystrix 设置
> 默认情况下，Spring Cloud Feign 会将所有的 Feign 客户端的方法都封装到 Hystrix 命令中进行服务保护
* 全局配置
> 如配置全局超时时间：hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=5000;
关闭熔断功能：hystrix.command.default.execution.timeout.enabled=false；
另外，在对 Hystrix 进行
配置之前，需要确认 feign.hystrix.enabled 参数没有被设置为 false，否则该参数设置会关闭 Feign 客户端的 Hystrix 支持。

* 指定命令配置，采用 hystrix.command.\<commandKey\> 作为前缀进行配置
> \<commandKey\>默认情况下会采用 Feign 客户端中的方法名作为标识，相同方法名的 Hystrix 配置共用。如：
hystrix.command.hello.execution.isolation.thread.timeoutInMilliseconds=5000

* 禁用 Hystrix
> feign.hystrix.enabled 设置为 false 将全局关闭 Hystrix 支持。
> 如果只想关闭某个服务客户端时，需要通过使用 @Scope("prototype") 注解为指定的客户端配置 Feign.Builder 实例，步骤：1.构建一个关闭的
Hystrix 的配置类；2.在 XxxService 的 @FeignClient 注解中，通过 configuration 参数引入配置类，详细见 HelloService2

# 服务降级
* 为 Feign 客户端的定义接口编写一个具体的接口实现类
> 如为 HelloService 接口实现一个服务降级类 HelloServiceFallback，其中每个重写方法
的实现逻辑都可以用来定义相应的服务降级逻辑
* 在服务绑定接口 HelloService 中，通过 @FeignClient 注解的 fallback 属性来指定对应的服务降级实现类

# 其它配置
* 请求压缩，开启请求与相应的压缩功能：feign.compression.request.enabled=true，feign.compression.response.enabled=true
> 还能对请求压缩做一些更细致的设置，feign.compression.request.mime-types=text/xml,application/xml,application/json #配置指定请求
压缩的请求数据类型，该设置为默认值；feign.compression.request.min-request-size=2048 #设置请求压缩的大小下限，该设置为默认值
* 日志配置
> Spring Cloud Feign 在构建被 @FeignClient 注解修饰的服务客户端时，会为每个客户端都创建一个 feign.Logger 实例。
> 可以在配置文件中使用 loggin.level.\<FeignClient\>的参数配置格式来开启指定 Feign 客户端的 DEBUG日志，其它<FeignClient>为 Feign 客户端
定义接口的完整路径。还需要在应用主类中加入 Logger.Level 的 Bean 创建 或 通过配置类 才能实现对 DEBUG 日志，因为 Logger.Level 默认为 NONE
级别。
> Logger.Level 的级别有：NONE、BASIC、HEADERS、FULL


