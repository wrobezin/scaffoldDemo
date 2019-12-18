package io.github.wrobezin.framework.common.check.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定字符串非空（不为null或""）。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringNotEmpty {
    boolean notEmpty() default true;

    @AliasFor("value")
    String invalidMessage() default "";

    @AliasFor("invalidMessage")
    String value() default "";
}
