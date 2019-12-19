package io.github.wrobezin.framework.common.check.object;

import io.github.wrobezin.framework.common.check.AbstractParameterValidator;
import io.github.wrobezin.framework.common.check.CompositeValidators;
import io.github.wrobezin.framework.common.check.ParameterValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import io.github.wrobezin.framework.common.check.annotation.CompositeValidator;
import io.github.wrobezin.framework.common.check.annotation.ObjectFieldVerify;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.TreeSet;

/**
 * 对象字段校验器。
 * 递归地根据字段上的注解进行校验，将调用所有被{@link CompositeValidator}标记的校验器。
 * 由于需要递归，与{@link CompositeValidators}存在循环依赖。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Slf4j
@CompositeValidator
public class ObjectFieldValidator extends AbstractParameterValidator<ObjectFieldVerify, Object> {
    private TreeSet<ParameterValidator> validators;

    @Override
    @SuppressWarnings("unchecked")
    protected VerifyResult verify(ObjectFieldVerify annotation, Object value) {
        if (validators == null) {
            validators = CompositeValidators.getInstance().getValidators();
        }
        VerifyResult result = VerifyResult.VALID;
        try {
            Class<?> objectClass = annotation.value();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                field.setAccessible(accessible);
                for (ParameterValidator validator : validators) {
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
