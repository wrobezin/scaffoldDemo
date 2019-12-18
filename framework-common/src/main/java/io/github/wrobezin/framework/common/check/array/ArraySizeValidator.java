package io.github.wrobezin.framework.common.check.array;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ArraySizeSatisfy;
import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import java.lang.reflect.Array;

/**
 * @author yuan
 * date: 2019/12/17
 */
@ComponentValidator
public class ArraySizeValidator extends AbstractParameterValidator<ArraySizeSatisfy, Object> {
    @Override
    protected VerifyResult verify(ArraySizeSatisfy annotation, Object value) {
        if (!value.getClass().isArray()) {
            return VerifyResult.VALID;
        }
        if (annotation.min() <= Array.getLength(value) && Array.getLength(value) <= annotation.max()) {
            return VerifyResult.VALID;
        }
        return VerifyResult.fail(annotation.invalidMessage());
    }
}
