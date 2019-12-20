package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import io.github.wrobezin.framework.common.check.annotation.CompositeValidator;
import java.util.function.Predicate;

/**
 * 字符串校验器链
 *
 * @author yuan
 * date: 2019/12/16
 */
@CompositeValidator
public class StringValidatorChain extends AbstractParameterValidatorChain<String> {
    @Override
    public Predicate<Class<?>> classSatisfy() {
        return String.class::isAssignableFrom;
    }

    @Override
    public Double getPriority() {
        return 7.0;
    }
}
