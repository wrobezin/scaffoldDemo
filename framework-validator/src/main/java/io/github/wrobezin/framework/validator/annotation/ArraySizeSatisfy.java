package io.github.wrobezin.framework.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定数组长度范围。
 *
 * @author yuan
 * date: 2019/12/17
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArraySizeSatisfy {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String invalidMessage() default "";
}
