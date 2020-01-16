package io.github.wrobezin.framework.validator.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定字符串满足给定正则表达式。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringRegexSatisfy {
    @AliasFor("value")
    String regex() default "^.*$";

    @AliasFor("regex")
    String value() default "^.*$";

    String invalidMessage() default "";
}
