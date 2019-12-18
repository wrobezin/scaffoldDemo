package io.github.wrobezin.framework.common.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 递归检验所标注对象的所有字段，根据字段上的注解进行校验。
 * 不包括父类字段。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectFieldVerify {
    /** 待校验对象所属类的字节码 */
    Class value() default Object.class;
}
