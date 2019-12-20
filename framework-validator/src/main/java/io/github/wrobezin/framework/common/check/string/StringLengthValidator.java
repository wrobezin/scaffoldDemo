package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.common.check.annotation.StringLengthSatisfy;

/**
 * 字符串长度校验器
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(StringValidatorChain.class)
public class StringLengthValidator extends AbstractParameterValidator<StringLengthSatisfy, String> {
    @Override
    protected VerifyResult verify(StringLengthSatisfy annotation, String value) {
        if (value.length() >= annotation.minLength() && value.length() <= annotation.maxLength()) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }

    @Override
    public Double getPriority() {
        return 5.0;
    }
}
