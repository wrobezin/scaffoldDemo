package io.github.wrobezin.framework.sign.strategy;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author yuan
 * date: 2019/12/26
 */
public abstract class AbstractSignCheckStrategy implements SignCheckStrategy {
    protected Predicate<HttpServletRequest> satisfy() {
        return request -> true;
    }

    @Override
    public boolean check(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .filter(satisfy())
                .isPresent();
    }
}
