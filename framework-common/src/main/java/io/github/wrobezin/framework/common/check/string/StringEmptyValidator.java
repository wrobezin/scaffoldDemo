package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.common.check.annotation.StringNotEmpty;
import org.springframework.util.StringUtils;

/**
 * 空字符串校验器
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(StringValidatorChain.class)
public class StringEmptyValidator extends AbstractParameterValidator<StringNotEmpty, String> {
    @Override
    protected VerifyResult verify(StringNotEmpty annotation, String value) {
        if (annotation.notEmpty() && StringUtils.isEmpty(value)) {
            return new VerifyResult(false, annotation.invalidMessage());
        }
        return VerifyResult.VALID;
    }

    @Override
    public Double getPriority() {
        return 1.0;
    }
}
