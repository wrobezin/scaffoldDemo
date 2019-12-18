package io.github.wrobezin.framework.common.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定Double取值范围。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleValueSatisfy {
    double min() default Double.MIN_VALUE;

    double max() default Double.MAX_VALUE;

    String invalidMessage() default "";
}
