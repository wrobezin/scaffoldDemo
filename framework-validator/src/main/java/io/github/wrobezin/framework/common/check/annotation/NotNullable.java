package io.github.wrobezin.framework.common.check.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定对象不可为null。
 *
 * @author yuan
 * date: 2019/12/17
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullable {
    @AliasFor("value")
    String invalidMessage() default "";

    @AliasFor("invalidMessage")
    String value() default "";
}
