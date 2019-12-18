package io.github.wrobezin.framework.common.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定Integer取值范围。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerValueSatisfy {
    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;

    String invalidMessage() default "";
}
