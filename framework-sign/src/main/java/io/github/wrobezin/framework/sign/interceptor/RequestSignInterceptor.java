package io.github.wrobezin.framework.sign.interceptor;

import io.github.wrobezin.framework.sign.annotation.SignCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author yuan
 * date: 2019/12/26
 */
@Component
@Slf4j
public class RequestSignInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 不是Controller方法的就直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        // 没有签名校验注解的，放行
        if (method.isAnnotationPresent(SignCheck.class)) {
            SignCheck signCheck = AnnotationUtils.findAnnotation(method, SignCheck.class);

            return true;
        }
        return true;
    }
}
