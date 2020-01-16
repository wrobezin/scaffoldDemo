package io.github.wrobezin.framework.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 只有加了这个注解的方法，其入参才会被检验。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterCheck {
}
