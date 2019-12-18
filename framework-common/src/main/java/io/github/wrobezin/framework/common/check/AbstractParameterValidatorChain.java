package io.github.wrobezin.framework.common.check;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象校验器链。
 * 采用模板方法模式。
 * TODO 扩展新校验器时需要修改子类的createValidatorChain()方法，违反开闭原则，可设法重构
 *
 * @param <T> 待验值类型
 * @author yuan
 * date: 2019/12/16
 */
public abstract class AbstractParameterValidatorChain<T> implements ValidatorChain<T> {
    private List<ParameterValidator<T>> chain;

    protected AbstractParameterValidatorChain() {
        this.chain = createValidatorChain();
    }

    @Override
    public VerifyResult chainVerify(AnnotatedElement annotatedElement, T value) {
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
     * 在{@code chainVerify}方法中将顺序调用该列表中的校验器。
     *
     * @return 校验器列表
     * @apiNote AbstractParameterValidatorChain构造方法执行时就会调用该方法，子类中若使用Spring进行依赖注入，
     * 由于父类构造方法先于子类构造方法执行，会导致调用该方法时校验器仍未被注入初始化，将得到null。子类在实
     * 现该方法时，应使用new等方式创建校验器对象。
     * @apiNote
     */
    protected List<ParameterValidator<T>> createValidatorChain() {
        return new ArrayList<>(0);
    }
}
