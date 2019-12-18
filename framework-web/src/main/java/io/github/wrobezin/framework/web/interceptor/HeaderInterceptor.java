package io.github.wrobezin.framework.web.interceptor;

import io.github.wrobezin.framework.common.request.annotation.PublicRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 请求头拦截器
 *
 * @author yuan
 * date: 2019/12/16
 */
@Slf4j
@Component
public class HeaderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            log.info("非HandlerMethod放行 : {}", request.getRequestURI());
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        if (method.isAnnotationPresent(PublicRequest.class)) {
            log.info("PublicRequest放行 : {}", request.getRequestURI());
            return true;
        }
        // 对请求头中没有fuck字段的请求进行拦截
        return Optional.ofNullable(request.getHeader("fuck")).map(s -> s.length() > 0).orElse(false);
    }
}
