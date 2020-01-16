package io.github.wrobezin.framework.validator.number;

import io.github.wrobezin.framework.validator.AbstractParameterValidator;
import io.github.wrobezin.framework.validator.VerifyResult;
import io.github.wrobezin.framework.validator.annotation.ComponentValidator;
import io.github.wrobezin.framework.validator.annotation.DoubleValueSatisfy;

/**
 * Double取值范围校验器
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(NumberValidatorChain.class)
public class DoubleValueValidator extends AbstractParameterValidator<DoubleValueSatisfy, Double> {
    @Override
    protected VerifyResult verify(DoubleValueSatisfy annotation, Double value) {
        if (value.compareTo(annotation.min()) >= 0 && value.compareTo(annotation.max()) <= 0) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
