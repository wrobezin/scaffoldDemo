package io.github.wrobezin.framework.validator.number;

import io.github.wrobezin.framework.validator.AbstractParameterValidatorChain;
import io.github.wrobezin.framework.validator.annotation.CompositeValidator;
import java.util.function.Predicate;

/**
 * 数值校验器链
 *
 * @author yuan
 * date: 2019/12/16
 */
@CompositeValidator
public class NumberValidatorChain extends AbstractParameterValidatorChain<Number> {
    @Override
    public Predicate<Class<?>> classSatisfy() {
        return Number.class::isAssignableFrom;
    }

    @Override
    public Double getPriority() {
        return 8.0;
    }
}
