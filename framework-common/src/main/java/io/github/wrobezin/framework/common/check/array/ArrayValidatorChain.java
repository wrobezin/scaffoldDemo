package io.github.wrobezin.framework.common.check.array;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import org.springframework.stereotype.Component;
import java.util.function.Predicate;

/**
 * @author yuan
 * date: 2019/12/17
 */
@Component
public class ArrayValidatorChain extends AbstractParameterValidatorChain<Object> {
    @Override
    public Predicate<Class<?>> classSatisfy() {
        return Class::isArray;
    }
}
