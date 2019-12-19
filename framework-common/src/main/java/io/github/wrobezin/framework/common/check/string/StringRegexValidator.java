package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.common.check.annotation.StringRegexSatisfy;

/**
 * 字符串正则校验器
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(StringValidatorChain.class)
public class StringRegexValidator extends AbstractParameterValidator<StringRegexSatisfy, String> {
    @Override
    protected VerifyResult verify(StringRegexSatisfy annotation, String value) {
        if (value.matches(annotation.regex())) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }

    @Override
    public Double getPriority() {
        return 20.0;
    }
}
