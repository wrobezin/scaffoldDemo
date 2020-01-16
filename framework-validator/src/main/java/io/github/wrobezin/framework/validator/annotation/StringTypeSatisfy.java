package io.github.wrobezin.framework.validator.annotation;

import io.github.wrobezin.framework.validator.string.StringTypeEnum;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规定字符串属于{@link StringTypeEnum}所枚举的某一特定类型。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringTypeSatisfy {
    @AliasFor("value")
    StringTypeEnum type() default StringTypeEnum.ORDINARY;

    @AliasFor("type")
    StringTypeEnum value() default StringTypeEnum.ORDINARY;

    String invalidMessage() default "";
}
