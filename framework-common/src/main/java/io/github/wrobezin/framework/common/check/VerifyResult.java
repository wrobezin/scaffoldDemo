package io.github.wrobezin.framework.common.check;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuan
 * date: 2019/12/16
 */
@Data
@AllArgsConstructor
public class VerifyResult {
    private boolean valid;
    private String message;

    public static final VerifyResult VALID = new VerifyResult(true, null);

    public static VerifyResult fail(String message) {
        return new VerifyResult(false, message);
    }
}
