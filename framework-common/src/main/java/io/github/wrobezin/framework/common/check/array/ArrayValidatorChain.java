package io.github.wrobezin.framework.common.check.array;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import io.github.wrobezin.framework.common.check.ParameterValidator;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

/**
 * @author yuan
 * date: 2019/12/17
 */
@Component
public class ArrayValidatorChain extends AbstractParameterValidatorChain<Object> {
    @Override
    protected List<ParameterValidator<Object>> createValidatorChain() {
        return Collections.singletonList(
                ArraySizeValidator.getInstance()
        );
    }
}
