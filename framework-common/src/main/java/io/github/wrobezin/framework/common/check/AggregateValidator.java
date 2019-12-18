package io.github.wrobezin.framework.common.check;

import io.github.wrobezin.framework.common.check.annotation.NotNullable;
import io.github.wrobezin.framework.common.check.array.ArrayValidatorChain;
import io.github.wrobezin.framework.common.check.number.NumberValidatorChain;
import io.github.wrobezin.framework.common.check.object.ObjectFieldValidator;
import io.github.wrobezin.framework.common.check.string.StringValidatorChain;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.AnnotatedElement;

/**
 * 聚合校验器，供web模块直接使用。
 * TODO 扩展新校验器链时需要在该类中添加，违反开闭原则，可设法重构。
 * TODO {@code verify}方法部分代码与{@code ObjectFieldValidator#verify}的部分代码存在重合，可设法重构。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Component
public class AggregateValidator {
    private final NumberValidatorChain numberValidatorChain;
    private final StringValidatorChain stringValidatorChain;
    private final ArrayValidatorChain arrayValidatorChain;
    private final ObjectFieldValidator objectFieldValidator;

    public AggregateValidator(NumberValidatorChain numberValidatorChain, StringValidatorChain stringValidatorChain, ArrayValidatorChain arrayValidatorChain, ObjectFieldValidator objectFieldValidator) {
        this.numberValidatorChain = numberValidatorChain;
        this.stringValidatorChain = stringValidatorChain;
        this.arrayValidatorChain = arrayValidatorChain;
        this.objectFieldValidator = objectFieldValidator;
    }

    public VerifyResult verify(final AnnotatedElement annotatedElement, final Object value) {
        VerifyResult result = VerifyResult.VALID;
        if (value == null) {
            if (annotatedElement.isAnnotationPresent(NotNullable.class)) {
                NotNullable notNullable = AnnotationUtils.findAnnotation(annotatedElement, NotNullable.class);
                return VerifyResult.fail(notNullable.invalidMessage());
            } else {
                return VerifyResult.VALID;
            }
        }
        if (value instanceof String) {
            result = stringValidatorChain.chainVerify(annotatedElement, (String) value);
        } else if (value instanceof Number) {
            result = numberValidatorChain.chainVerify(annotatedElement, (Number) value);
        } else if (value.getClass().isArray()) {
            result = arrayValidatorChain.chainVerify(annotatedElement, value);
        } else if (!value.getClass().isPrimitive()) {
            result = objectFieldValidator.verify(annotatedElement, value);
        }
        return result;
    }
}
