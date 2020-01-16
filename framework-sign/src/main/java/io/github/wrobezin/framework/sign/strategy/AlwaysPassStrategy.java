package io.github.wrobezin.framework.sign.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuan
 * date: 2019/12/26
 */
public final class AlwaysPassStrategy implements SignCheckStrategy {
    @Override
    public boolean check(HttpServletRequest request) {
        return true;
    }
}
