package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.StringTypeSatisfy;

/**
 * @author yuan
 * date: 2019/12/16
 */
public class StringTypeValidator extends AbstractParameterValidator<StringTypeSatisfy, String> {
    private static StringTypeValidator singleton = new StringTypeValidator();

    private StringTypeValidator() {

    }

    static StringTypeValidator getInstance() {
        return singleton;
    }

    @Override
    protected VerifyResult verify(StringTypeSatisfy annotation, String value) {
        if (StringTypeEnum.isType(annotation.type(), value)) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
