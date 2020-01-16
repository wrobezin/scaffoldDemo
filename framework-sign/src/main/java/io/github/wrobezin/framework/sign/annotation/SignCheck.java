package io.github.wrobezin.framework.sign.annotation;

import io.github.wrobezin.framework.sign.strategy.AlwaysPassStrategy;
import io.github.wrobezin.framework.sign.strategy.SignCheckStrategy;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被该注解标记的方法需要签名校验
 *
 * @author yuan
 * date: 2019/12/26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignCheck {
    @AliasFor("value")
    Class<? extends SignCheckStrategy> strategy() default AlwaysPassStrategy.class;

    @AliasFor("strategy")
    Class<? extends SignCheckStrategy> value() default AlwaysPassStrategy.class;

    String invalidMessage() default "InvalidSign";
}
