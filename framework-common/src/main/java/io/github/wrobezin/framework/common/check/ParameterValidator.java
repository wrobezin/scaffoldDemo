package io.github.wrobezin.framework.common.check;

import java.lang.reflect.AnnotatedElement;
import java.util.function.Predicate;

/**
 * @param <T> 待验值类型
 * @author yuan
 * date: 2019/12/16
 */
public interface ParameterValidator<T> {
    VerifyResult verify(AnnotatedElement annotatedElement, T value);

    default Predicate<Class<?>> classSatisfy() {
        return c -> true;
    }
}
