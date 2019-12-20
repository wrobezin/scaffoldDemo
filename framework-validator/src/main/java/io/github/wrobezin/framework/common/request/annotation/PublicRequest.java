package io.github.wrobezin.framework.common.request.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 带有这个注解的方法不会被拦截。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicRequest {
}
