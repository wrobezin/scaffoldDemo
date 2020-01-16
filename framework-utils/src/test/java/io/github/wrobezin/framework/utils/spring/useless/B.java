package io.github.wrobezin.framework.utils.spring.useless;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用来测试工具类，不交给Spring管理
 *
 * @author yuan
 * date: 2019/12/26
 */
@Data
@AllArgsConstructor
public class B {
    private String message;
}
