package io.github.wrobezin.framework.common.check.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuan
 * date: 2019/12/18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentValidator {
    @AliasFor("value")
    Class<?> type() default Object.class;

    @AliasFor("type")
    Class<?> value() default Object.class;
}
