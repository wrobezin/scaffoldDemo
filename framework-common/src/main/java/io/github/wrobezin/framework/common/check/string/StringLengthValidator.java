package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.StringLengthSatisfy;

/**
 * @author yuan
 * date: 2019/12/16
 */
public class StringLengthValidator extends AbstractParameterValidator<StringLengthSatisfy, String> {
    private static StringLengthValidator singleton = new StringLengthValidator();

    private StringLengthValidator() {

    }

    static StringLengthValidator getInstance() {
        return singleton;
    }

    @Override
    protected VerifyResult verify(StringLengthSatisfy annotation, String value) {
        if (value.length() >= annotation.minLength() && value.length() <= annotation.maxLength()) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
