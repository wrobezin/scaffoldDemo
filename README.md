本项目旨在实现一个自用的 SpringBoot 脚手架环境，提供各种常用功能，供新项目直接使用。

已实现：
+ 基于注解的 Controller 入参校验

基本完成，待完善：
+ 插件式 Swagger 模块

开发中：
无

计划中：
+ 常用工具类集成
+ 基于注解的请求签名校验
+ 基于注解的 PO-VO 转换
+ 集成 JWT
+ 可插拔的 Shiro 和 SpringSecurity
+ 带后台的独立日志模块
+ 定时任务调度系统
+ 数据脱敏模块

## 基于注解的 Controller 入参校验

### 对应模块

`framework-validator`

### 使用方法

添加对 `framework-validator` 模块的依赖。

在 Controller 的方法参数和实体类字段上添加注解，然后通过 AOP 以 `@around` 方式调用 `AggregateValidator` 进行校验。

详见 `framework-validator-example` 模块 `ValidatorUseController`、`ControllerParampeterCheckAspect`、`Student` 等类的代码。

### 单元测试

`framework-validator-example` 模块的 `ValidatorUseControllerTest` 类。

### 扩展方法

#### 在已有校验器链中添加新的校验器
+ 编写新的校验注解。
+ 编写新的校验器类，继承 `AbstractParameterValidator` 类或实现 `ParameterValidator` 接口，针对新写的注解作校验。
+ 在新写的校验器类上添加 `@ComponentValidator` 注解，注册到校验器链中。

#### 添加新的校验器链
+ 编写新的校验器链类，继承 `AbstractParameterValidatorChain` 类或实现 `ValidatorChain` 接口。
+ 在新写的校验器类上添加 `@CompositeValidator` 注解。

#### 注意事项

如果对校验器的执行顺序有需求，就必须注意设计好优先级。尤其要注意新链的优先级，因为在聚合校验中只会命中一条链。

## 插件式 Swagger 模块

### 对应模块

`framework-swagger`

### 注意事项

本模块采用的 Swagger2 版本是 2.9.2。

Swagger2 本身是存在一些 bug 的。

比如，如果 Controller 的方法参数带有除 `[@PathVariable, @RequestBody, @RequestPart, @RequestParam, @RequestHeader, @ModelAttribute]` 之外的其它注解（比如参数校验模块所提供的注解），Swagger 会把它们当作是 `@RequestBody`。

在 Swagger2 的 github 上有不少关于该 bug 的讨论，比如：
+ https://github.com/springfox/springfox/issues/1965
+ https://github.com/springfox/springfox/issues/2855
+ https://github.com/springfox/springfox/issues/3000

作者似乎提到切换到 3.0.0-SNAPSHOT 版本即可，我尝试后发现不能解决问题。

该 bug 是 {@link springfox.documentation.spring.web.readers.parameter.ParameterTypeReader#findParameterType} 逻辑错误所致，本模块将该类从 jar 包中取出并进行了修改，编译时会优先采用本模块中的代码。

被 `@PostMapping` 注解标记的方法，如果其参数不添加 `@RequestParam` 注解，方法也未指定 `consumes`，那么既可以以 url 参数的形式接收这些参数（在 Swagger2 中对应 `query` 型），也可以以表单的形式接收这些参数（在 Swagger2 中对应 `form` 型）。使用本模块时，若未指定 POST 接口的 `consumes`，也未对参数使用 `@RequestParam`注解，那么参数在生成的文档中默认会被视作 `query` 型，如果希望在生成的文档中默认使用 `form` 型，可以对本模块中的 `springfox.documentation.spring.web.readers.parameter.ParameterTypeDeterminer` 类进行修改，这个类也是从 Swagger2 的 jar 包中取出来的。

### 使用方法

在启动类所在模块添加对 `framework-swagger` 模块的依赖，在配置文件中配置项目信息，配置项对应本模块中的 `io.github.wrobezin.framework.swagger.property.SwaggerApplicationConfig` 类。

在需要生成文档的 Controller 上添加 `@RegisterToSwagger` 注解。

按照正常的 Swagger 使用方法在类上添加文档相关注解。

### 单元测试

待添加
