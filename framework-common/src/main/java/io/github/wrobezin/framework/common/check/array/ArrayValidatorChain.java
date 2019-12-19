package io.github.wrobezin.framework.common.check.array;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import io.github.wrobezin.framework.common.check.annotation.CompositeValidator;
import java.util.function.Predicate;

/**
 * 数组校验器链。
 *
 * @author yuan
 * date: 2019/12/17
 */
@CompositeValidator
public class ArrayValidatorChain extends AbstractParameterValidatorChain<Object> {
    @Override
    public Predicate<Class<?>> classSatisfy() {
        return Class::isArray;
    }

    @Override
    public Double getPriority() {
        return 9.0;
    }
}
