package io.github.wrobezin.framework.validator.number;

import io.github.wrobezin.framework.validator.AbstractParameterValidator;
import io.github.wrobezin.framework.validator.VerifyResult;
import io.github.wrobezin.framework.validator.annotation.ComponentValidator;
import io.github.wrobezin.framework.validator.annotation.IntegerValueSatisfy;

/**
 * Integer取值范围校验器
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(NumberValidatorChain.class)
public class IntegerValueValidator extends AbstractParameterValidator<IntegerValueSatisfy, Integer> {
    @Override
    protected VerifyResult verify(IntegerValueSatisfy annotation, Integer value) {
        if (value.compareTo(annotation.min()) >= 0 && value.compareTo(annotation.max()) <= 0) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
