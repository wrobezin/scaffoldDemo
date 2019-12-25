package io.github.wrobezin.framework.example.validator.entity;

import io.github.wrobezin.framework.common.check.annotation.StringRegexSatisfy;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuan
 * date: 2019/12/16
 */
@Data
@AllArgsConstructor
public class TestEntity {
    @StringRegexSatisfy(value = "a", invalidMessage = "a不是a")
    private String a;
    @StringRegexSatisfy(value = "b", invalidMessage = "b不是b")
    private String b;
}
