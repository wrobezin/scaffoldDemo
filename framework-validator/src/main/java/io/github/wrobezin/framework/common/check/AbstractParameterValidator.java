package io.github.wrobezin.framework.common.check;


import io.github.wrobezin.framework.common.check.annotation.NotNullable;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 抽象校验器。
 * 采用模板方法模式。
 *
 * @param <A> 注解类型
 * @param <T> 待验值类型
 * @author yuan
 * date: 2019/12/16
 */
public abstract class AbstractParameterValidator<A extends Annotation, T> implements ParameterValidator<T> {
    private Class<A> annotationClass;

    @SuppressWarnings("unchecked")
    protected AbstractParameterValidator() {
        // 获取子类对应的注解字节码A.class
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type annotationType = type.getActualTypeArguments()[0];
            this.annotationClass = (Class<A>) Class.forName(annotationType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public VerifyResult verify(AnnotatedElement annotatedElement, T value) {
        // 如果待验值为null，再判断是否有NotNullable注解
        if (value == null) {
            if (annotatedElement.isAnnotationPresent(NotNullable.class)) {
                NotNullable notNullable = AnnotationUtils.findAnnotation(annotatedElement, NotNullable.class);
                return VerifyResult.fail(notNullable.invalidMessage());
            } else {
                return VerifyResult.VALID;
            }
        }
        // 如果注解类型不对应，视作校验通过
        if (!annotatedElement.isAnnotationPresent(annotationClass)) {
            return VerifyResult.VALID;
        }
        // 调用实现类的校验逻辑
        return verify(AnnotationUtils.findAnnotation(annotatedElement, annotationClass), value);
    }

    /**
     * 子类实现，具体的校验逻辑
     *
     * @param annotation 校验注解
     * @param value      待校验对象
     * @return 校验结果
     */
    protected abstract VerifyResult verify(A annotation, T value);
}
