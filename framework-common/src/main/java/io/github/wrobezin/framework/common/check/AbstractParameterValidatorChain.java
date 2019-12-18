package io.github.wrobezin.framework.common.check;

import io.github.wrobezin.framework.common.check.annotation.ComponentValidator;
import io.github.wrobezin.framework.utils.spring.PackageScanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private List<ParameterValidator<T>> chain;
    private Class<T> valueClass;

    @SuppressWarnings("unchecked")
    protected AbstractParameterValidatorChain() {
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type annotationType = type.getActualTypeArguments()[0];
            this.valueClass = (Class<T>) Class.forName(annotationType.getTypeName());
        } catch (ClassNotFoundException e) {
            log.error(e.toString());
        }
        this.chain = createValidatorChain();
    }

    @Override
    public VerifyResult verify(AnnotatedElement annotatedElement, T value) {
        // 迭代检验
        for (ParameterValidator<T> validator : chain) {
            VerifyResult verifyResult = validator.verify(annotatedElement, value);
            // 短路
            if (!verifyResult.isValid()) {
                return verifyResult;
            }
        }
        return VerifyResult.VALID;
    }

    /**
     * 子类实现，创建校验器列表。
     * 在{@code verify}方法中将顺序调用该列表中的校验器。
     *
     * @return 校验器列表
     * @apiNote AbstractParameterValidatorChain构造方法执行时就会调用该方法，子类中若使用Spring进行依赖注入，
     * 由于父类构造方法先于子类构造方法执行，会导致调用该方法时校验器仍未被注入初始化，将得到null。子类在实
     * 现该方法时，应使用new等方式创建校验器对象。
     * @apiNote
     */
    @SuppressWarnings("unchecked")
    protected List<ParameterValidator<T>> createValidatorChain() {
        List<ParameterValidator<T>> result = new ArrayList<>();
        List<Class<?>> validatorClasses = PackageScanUtils.classScan(this.getClass().getPackage().getName())
                .stream()
                .filter(c -> Optional
                        .ofNullable(AnnotationUtils.findAnnotation(c, ComponentValidator.class))
                        .map(ComponentValidator::type)
                        .map(valueClass::equals)
                        .orElse(false)
                ).collect(Collectors.toList());
        try {
            for (Class<?> validatorClass : validatorClasses) {
                result.add((ParameterValidator<T>) validatorClass.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.toString());
        }
        return result;
    }
}
