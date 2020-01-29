package io.github.wrobezin.framework.validator;

import io.github.wrobezin.framework.validator.annotation.CompositeValidator;
import io.github.wrobezin.framework.utils.spring.PackageScanUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.TreeSet;

/**
 * 单例,用于封装所有{@link CompositeValidator}标记的校验器。
 *
 * @author yuan
 * date: 2019/12/18
 */
@Getter
@Slf4j
public class CompositeValidators {
    private static CompositeValidators instance = new CompositeValidators();
    private TreeSet<ParameterValidator> validators;

    private CompositeValidators() {
        this.validators = new TreeSet<>();
        // 扫包获取@CompositeValidator标记的类，创建对象放入校验器集合
        PackageScanUtils.classScan(this.getClass().getPackage().getName())
                .stream()
                .filter(c -> c.isAnnotationPresent(CompositeValidator.class))
                .forEach(c -> {
                    try {
                        validators.add((ParameterValidator) c.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.error(e.toString());
                    }
                });
    }

    public static CompositeValidators getInstance() {
        return instance;
    }
}
