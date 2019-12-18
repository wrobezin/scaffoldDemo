package io.github.wrobezin.framework.common.check.object;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.ParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.ObjectFieldVerify;
import io.github.wrobezin.framework.common.check.array.ArrayValidatorChain;
import io.github.wrobezin.framework.common.check.number.NumberValidatorChain;
import io.github.wrobezin.framework.common.check.string.StringValidatorChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 因为涉及递归，不好写成链式，否则可能会出现循环依赖。
 * 如需扩展，可另写一个{@code ObjectValidatorChain}，但不把{@code ObjectFieldValidator}放入该链中，
 * 而是在{@code ObjectFieldValidator}中依赖该新链。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Slf4j
@Component
public class ObjectFieldValidator extends AbstractParameterValidator<ObjectFieldVerify, Object> {
    private final NumberValidatorChain numberValidatorChain;
    private final StringValidatorChain stringValidatorChain;
    private final ArrayValidatorChain arrayValidatorChain;
    private final List<ParameterValidator> chains;

    public ObjectFieldValidator(NumberValidatorChain numberValidatorChain, StringValidatorChain stringValidatorChain, ArrayValidatorChain arrayValidatorChain) {
        this.numberValidatorChain = numberValidatorChain;
        this.stringValidatorChain = stringValidatorChain;
        this.arrayValidatorChain = arrayValidatorChain;
        this.chains = Arrays.asList(numberValidatorChain, stringValidatorChain, arrayValidatorChain, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected VerifyResult verify(ObjectFieldVerify annotation, Object value) {
        VerifyResult result = VerifyResult.VALID;
        try {
            Class<?> objectClass = annotation.value();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                field.setAccessible(accessible);
                for (ParameterValidator validator : chains) {
                    if (validator.classSatisfy().test(field.getType())) {
                        result = validator.verify(field, fieldValue);
                        break;
                    }
                }
                if (!result.isValid()) {
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
