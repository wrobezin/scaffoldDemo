package io.github.wrobezin.framework.common.check.number;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.common.check.annotation.DoubleValueSatisfy;

/**
 * Double取值范围校验器
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(NumberValidatorChain.class)
public class DoubleValueValidator extends AbstractParameterValidator<DoubleValueSatisfy, Number> {
    @Override
    protected VerifyResult verify(DoubleValueSatisfy annotation, Number value) {
        if (value.doubleValue() >= annotation.min() && value.doubleValue() <= annotation.max()) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
