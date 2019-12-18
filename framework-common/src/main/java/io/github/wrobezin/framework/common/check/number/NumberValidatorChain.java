package io.github.wrobezin.framework.common.check.number;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import io.github.wrobezin.framework.common.check.ParameterValidator;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

/**
 * 数值校验器链
 *
 * @author yuan
 * date: 2019/12/16
 */
@Component
public class NumberValidatorChain extends AbstractParameterValidatorChain<Number> {
    @Override
    protected List<ParameterValidator<Number>> createValidatorChain() {
        return Arrays.asList(
                IntegerValueValidator.getInstance(),
                DoubleValueValidator.getInstance()
        );
    }
}
