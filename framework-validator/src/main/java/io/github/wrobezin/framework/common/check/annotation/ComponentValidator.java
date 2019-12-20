package io.github.wrobezin.framework.common.check.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件校验器
 * 该注解标记的校验器将被注册到{@code registeredChain}对应的容器校验器中。
 *
 * @author yuan
 * date: 2019/12/18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentValidator {
    @AliasFor("value")
    Class<?> registeredChain() default Object.class;

    @AliasFor("registeredChain")
    Class<?> value() default Object.class;
}
