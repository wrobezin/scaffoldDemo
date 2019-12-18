package io.github.wrobezin.framework.common.check.string;

import io.github.wrobezin.framework.common.check.AbstractParameterValidatorChain;
import org.springframework.stereotype.Component;
import java.util.function.Predicate;

/**
 * 字符串校验器链
 *
 * @author yuan
 * date: 2019/12/16
 */
@Component
public class StringValidatorChain extends AbstractParameterValidatorChain<String> {
    @Override
    public Predicate<Class<?>> classSatisfy() {
        return String.class::isAssignableFrom;
    }
}
