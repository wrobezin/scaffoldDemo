package io.github.wrobezin.framework.common.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 容器校验器
 * 该注解标记的校验器可以容纳组件校验器。
 * {@link io.github.wrobezin.framework.common.check.CompositeValidators}将持有被标记的类的一个对象。
 *
 * @author yuan
 * date: 2019/12/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompositeValidator {
}
