package io.github.wrobezin.framework.common.check;

import java.lang.reflect.AnnotatedElement;
import java.util.function.Predicate;

/**
 * 参数校验器
 *
 * @param <T> 待验值类型
 * @author yuan
 * date: 2019/12/16
 */
public interface ParameterValidator<T> extends Comparable<ParameterValidator<T>> {
    /** 默认优先级 */
    Double DEFAULT_PRIORITY = 10.0;

    /**
     * 校验
     *
     * @param annotatedElement 参数或字段
     * @param value            待验值
     * @return 校验结果
     */
    VerifyResult verify(AnnotatedElement annotatedElement, T value);

    /**
     * @apiNote 实现类若需要覆盖此方法，应注意TreeSet对重复元素的判断规则，如果只比较优先级可能会导致元素丢失
     */
    @Override
    default int compareTo(ParameterValidator<T> other) {
        int compareResult = this.getPriority().compareTo(other.getPriority());
        return compareResult != 0 ? compareResult : Integer.compare(this.hashCode(), other.hashCode());
    }

    /**
     * @return 判断待验值类型是否匹配本校验器的谓词
     */
    default Predicate<Class<?>> classSatisfy() {
        return c -> true;
    }

    /**
     * @return 优先级
     */
    default Double getPriority() {
        return DEFAULT_PRIORITY;
    }
}
