package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.StringRegexSatisfy;

/**
 * @author yuan
 * date: 2019/12/16
 */
public class StringRegexValidator extends AbstractParameterValidator<StringRegexSatisfy, String> {
    private static StringRegexValidator singleton = new StringRegexValidator();

    private StringRegexValidator() {

    }

    static StringRegexValidator getInstance() {
        return singleton;
    }

    @Override
    protected VerifyResult verify(StringRegexSatisfy annotation, String value) {
        if (value.matches(annotation.regex())) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
