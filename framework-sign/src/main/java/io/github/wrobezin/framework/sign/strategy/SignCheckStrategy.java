package io.github.wrobezin.framework.sign.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuan
 * date: 2019/12/26
 */
public interface SignCheckStrategy {
    boolean check(HttpServletRequest request);
}
