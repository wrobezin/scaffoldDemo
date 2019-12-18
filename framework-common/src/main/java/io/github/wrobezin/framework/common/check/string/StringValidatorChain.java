package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import io.github.wrobezin.framework.common.check.ParameterValidator;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串校验器链
 *
 * @author yuan
 * date: 2019/12/16
 */
@Component
public class StringValidatorChain extends AbstractParameterValidatorChain<String> {
    @Override
    protected List<ParameterValidator<String>> createValidatorChain() {
        return Arrays.asList(
                StringEmptyValidator.getInstance(),
                StringLengthValidator.getInstance(),
                StringTypeValidator.getInstance(),
                StringRegexValidator.getInstance()
        );
    }
}
