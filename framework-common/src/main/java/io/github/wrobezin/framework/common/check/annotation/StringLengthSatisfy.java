package io.github.wrobezin.framework.common.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定字符串长度范围。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringLengthSatisfy {

    int minLength() default 0;

    int maxLength() default Integer.MAX_VALUE;

    String invalidMessage() default "";
}
