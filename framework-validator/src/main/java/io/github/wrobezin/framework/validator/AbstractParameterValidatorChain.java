package io.github.wrobezin.framework.validator;

import io.github.wrobezin.framework.validator.annotation.ComponentValidator;
import io.github.wrobezin.framework.utils.resource.PackageScanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;
import java.util.TreeSet;

/**
 * 抽象校验器链。
 * 采用模板方法模式。
 *
 * @param <T> 待验值类型
 * @author yuan
 * date: 2019/12/16
 */
@Slf4j
public abstract class AbstractParameterValidatorChain<T> implements ValidatorChain<T> {
    /** 校验器链 */
    private TreeSet<ParameterValidator<T>> chain;

    protected AbstractParameterValidatorChain() {
        this.chain = createValidatorChain();
    }

    @Override
    public VerifyResult verify(AnnotatedElement annotatedElement, T value) {
        // 迭代检验
        for (ParameterValidator<T> validator : chain) {
            // 类型不匹配，跳过
            if (!validator.classSatisfy().test(value.getClass())) {
                continue;
            }
            VerifyResult verifyResult = validator.verify(annotatedElement, value);
            // 短路
            if (!verifyResult.isValid()) {
                return verifyResult;
            }
        }
        return VerifyResult.VALID;
    }

    /**
     * 创建校验器链。
     * 在{@code verify}方法中将按优先级从小到大的顺序调用校验器链中的校验器。
     *
     * @return 校验器集合
     * @apiNote 子类如需覆盖此方法，建议使用new形式创建校验器，如果使用Spring注入可能会导致NPE。这是因为该方法在
     * 父类构造方法中被调用时，子类构造方法未执行，所以子类所依赖的组件将未被Spring注入，皆为null。
     */
    @SuppressWarnings("unchecked")
    protected TreeSet<ParameterValidator<T>> createValidatorChain() {
        TreeSet<ParameterValidator<T>> result = new TreeSet<>();
        // 扫包获取@ComponentValidator标记的校验器类字节码，创建被注册到本链的校验器对象，并添加到本链的校验器集合中
        PackageScanUtils.classScan(this.getClass().getPackage().getName())
                .stream()
                .filter(c -> Optional
                        .ofNullable(AnnotationUtils.findAnnotation(c, ComponentValidator.class))
                        .map(ComponentValidator::registeredChain)
                        .map(this.getClass()::equals)
                        .orElse(false)
                )
                .forEach(c -> {
                    try {
                        result.add((ParameterValidator<T>) c.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.error(e.toString());
                    }
                });
        return result;
    }
}
