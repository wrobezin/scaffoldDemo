package io.github.wrobezin.framework.common.check.number;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.IntegerValueSatisfy;

/**
 * @author yuan
 * date: 2019/12/16
 */
public class IntegerValueValidator extends AbstractParameterValidator<IntegerValueSatisfy, Number> {
    private static IntegerValueValidator singleton = new IntegerValueValidator();

    private IntegerValueValidator() {

    }

    static IntegerValueValidator getInstance() {
        return singleton;
    }

    @Override
    protected VerifyResult verify(IntegerValueSatisfy annotation, Number value) {
        if (value.intValue() >= annotation.min() && value.intValue() <= annotation.max()) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
