本项目旨在实现一个自用的 SpringBoot 脚手架环境，提供各种常用功能，供新项目直接使用。

已实现：
+ 基于注解的 Controller 入参校验

开发中：
+ 独立 Swagger 模块

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

### 使用方法

添加对 `framework-validator` 模块的依赖。

在 Controller 的方法参数和实体类字段上添加注解，然后通过 AOP 以 `@around` 方式调用 `AggregateValidator` 进行校验。

详见 `web` 模块 `ValidatorUseController`、`ControllerParampeterCheckAspect` 两个类的代码 及 `business` 模块 `Student` 等类的代码。

### 单元测试

`web` 模块的 `ValidatorUseControllerTest` 类。

### 扩展方法

#### 在已有校验器链中添加新的校验器
+ 编写新的校验注解。
+ 编写新的校验器类，继承 `AbstractParameterValidator` 类或实现 `ParameterValidator` 接口，针对新写的注解作校验。
+ 在新写的校验器类上添加 `@ComponentValidator` 注解，注册到校验器链中。

#### 添加新的校验器链
+ 编写新的校验器链类，继承 `AbstractParameterValidatorChain` 类或实现 `ValidatorChain` 接口。
+ 在新写的校验器类上添加 `@CompositeValidator` 注解。

#### 注意点

需要设计好优先级。尤其要注意新链的优先级，因为在聚合校验中只会命中一条链。
