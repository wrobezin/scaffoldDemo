package io.github.wrobezin.framework.validator.object;

import io.github.wrobezin.framework.validator.AbstractParameterValidator;
import io.github.wrobezin.framework.validator.CompositeValidators;
import io.github.wrobezin.framework.validator.ParameterValidator;
import io.github.wrobezin.framework.validator.VerifyResult;
import io.github.wrobezin.framework.validator.annotation.CompositeValidator;
import io.github.wrobezin.framework.validator.annotation.ObjectFieldVerify;
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
                        // 只命中一类校验器
                        break;
                    }
                }
                // 只要一个字段校验不通过就返回
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
