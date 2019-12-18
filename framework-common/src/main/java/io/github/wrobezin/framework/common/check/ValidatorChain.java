package io.github.wrobezin.framework.common.check;

import java.lang.reflect.AnnotatedElement;

/**
 * @author yuan
 * date: 2019/12/16
 */
public interface ValidatorChain<T> {
    VerifyResult chainVerify(AnnotatedElement annotatedElement, T value);
}
