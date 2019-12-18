package io.github.wrobezin.framework.common.check.number;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.DoubleValueSatisfy;

/**
 * @author yuan
 * date: 2019/12/16
 */
public class DoubleValueValidator extends AbstractParameterValidator<DoubleValueSatisfy, Number> {
    private static DoubleValueValidator singleton = new DoubleValueValidator();

    private DoubleValueValidator() {

    }

    static DoubleValueValidator getInstance() {
        return singleton;
    }

    @Override
    protected VerifyResult verify(DoubleValueSatisfy annotation, Number value) {
        if (value.doubleValue() >= annotation.min() && value.doubleValue() <= annotation.max()) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
