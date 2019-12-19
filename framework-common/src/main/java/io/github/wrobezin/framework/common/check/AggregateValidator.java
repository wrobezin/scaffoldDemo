package io.github.wrobezin.framework.common.check;

import io.github.wrobezin.framework.common.check.annotation.NotNullable;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.AnnotatedElement;
import java.util.TreeSet;

/**
 * 聚合校验器，供web模块直接使用。
 *
 * @author yuan
 * date: 2019/12/16
 */
@Component
public class AggregateValidator {
    private TreeSet<ParameterValidator> validators = CompositeValidators.getInstance().getValidators();

    @SuppressWarnings("unchecked")
    public VerifyResult verify(final AnnotatedElement annotatedElement, final Object value) {
        VerifyResult result = VerifyResult.VALID;
        // 判空
        if (value == null) {
            if (annotatedElement.isAnnotationPresent(NotNullable.class)) {
                NotNullable notNullable = AnnotationUtils.findAnnotation(annotatedElement, NotNullable.class);
                return VerifyResult.fail(notNullable.invalidMessage());
            } else {
                return VerifyResult.VALID;
            }
        }
        // 迭代校验
        for (ParameterValidator validator : validators) {
            if (validator.classSatisfy().test(value.getClass())) {
                result = validator.verify(annotatedElement, value);
                break;
            }
        }
        return result;
    }
}
