package io.github.wrobezin.framework.common.check.number;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.common.check.annotation.IntegerValueSatisfy;

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
