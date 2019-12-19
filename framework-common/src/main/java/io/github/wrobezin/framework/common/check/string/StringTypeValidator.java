package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.common.check.annotation.StringTypeSatisfy;

/**
 * 字符串类型校验器
 * 支持{@link StringTypeEnum}中的字符串类型
 *
 * @author yuan
 * date: 2019/12/16
 */
@ComponentValidator(StringValidatorChain.class)
public class StringTypeValidator extends AbstractParameterValidator<StringTypeSatisfy, String> {
    @Override
    protected VerifyResult verify(StringTypeSatisfy annotation, String value) {
        if (StringTypeEnum.isType(annotation.type(), value)) {
            return VerifyResult.VALID;
        }
        return new VerifyResult(false, annotation.invalidMessage());
    }
}
