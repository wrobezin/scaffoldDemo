package io.github.wrobezin.framework.web.aop;

import io.github.wrobezin.framework.business.entity.RequestResult;
import io.github.wrobezin.framework.common.check.AggregateValidator;
import io.github.wrobezin.framework.common.check.VerifyResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Parameter;

/**
 * @author yuan
 * date: 2019/12/16
 */
@Aspect
@Slf4j
@Component
public class ControllerParampeterCheckAspect {
    private final AggregateValidator validator;

    public ControllerParampeterCheckAspect(AggregateValidator validator) {
        this.validator = validator;
    }

    @Pointcut("execution(public * io.github.wrobezin.framework.web.controller..*.*(..)) && @annotation(io.github.wrobezin.framework.common.check.annotation.ParameterCheck)")
    public void methodNeedVerified() {
    }

    @Around("methodNeedVerified()")
    public Object parameterCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();
        VerifyResult result = VerifyResult.VALID;
        for (int i = 0; i < parameters.length; i++) {
            result = validator.verify(parameters[i], args[i]);
            if (!result.isValid()) {
                return RequestResult.fail(result.getMessage());
            }
        }
        return joinPoint.proceed();
    }
}
