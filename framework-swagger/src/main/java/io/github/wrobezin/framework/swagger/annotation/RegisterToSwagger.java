package io.github.wrobezin.framework.swagger.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuan
 * date: 2019/12/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterToSwagger {
    String DEFAULT_GROUP_NAME = "default";

    /** 组名 */
    @AliasFor("value")
    String[] groupName() default DEFAULT_GROUP_NAME;

    @AliasFor("groupName")
    String[] value() default DEFAULT_GROUP_NAME;
}
