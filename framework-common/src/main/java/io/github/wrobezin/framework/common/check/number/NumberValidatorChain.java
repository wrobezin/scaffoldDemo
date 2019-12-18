package io.github.wrobezin.framework.common.check.number;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import org.springframework.stereotype.Component;
import java.util.function.Predicate;

/**
 * 数值校验器链
 *
 * @author yuan
 * date: 2019/12/16
 */
@Component
public class NumberValidatorChain extends AbstractParameterValidatorChain<Number> {
   @Override
    public Predicate<Class<?>> classSatisfy() {
        return Number.class::isAssignableFrom;
    }
}
