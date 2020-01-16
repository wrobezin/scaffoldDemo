package io.github.wrobezin.framework.validator.number;

import io.github.wrobezin.framework.validator.AbstractParameterValidator;
import io.github.wrobezin.framework.validator.VerifyResult;
import io.github.wrobezin.framework.validator.annotation.BigDecimalValueSatisfy;
import io.github.wrobezin.framework.validator.annotation.ComponentValidator;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

/**
 * @author yuan
 * date: 2019/12/19
 */
@ComponentValidator(NumberValidatorChain.class)
public class BigDecimalValueValidator extends AbstractParameterValidator<BigDecimalValueSatisfy, BigDecimal> {
    @Override
    protected VerifyResult verify(BigDecimalValueSatisfy annotation, BigDecimal value) {
        if (!StringUtils.isEmpty(annotation.min()) && value.compareTo(new BigDecimal(annotation.min())) < 0) {
            return VerifyResult.fail(annotation.invalidMessage());
        }
        if (!StringUtils.isEmpty(annotation.max()) && value.compareTo(new BigDecimal(annotation.max())) > 0) {
            return VerifyResult.fail(annotation.invalidMessage());
        }
        return VerifyResult.VALID;
    }
}
