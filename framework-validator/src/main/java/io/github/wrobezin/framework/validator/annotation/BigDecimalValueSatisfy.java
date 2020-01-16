package io.github.wrobezin.framework.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuan
 * date: 2019/12/19
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimalValueSatisfy {
    String min() default "";

    String max() default "";

    String invalidMessage() default "";
}
