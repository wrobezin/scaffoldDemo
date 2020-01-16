package io.github.wrobezin.framework.example.validator.entity;

import io.github.wrobezin.framework.validator.annotation.ArraySizeSatisfy;
import io.github.wrobezin.framework.validator.annotation.IntegerValueSatisfy;
import io.github.wrobezin.framework.validator.annotation.NotNullable;
import io.github.wrobezin.framework.validator.annotation.ObjectFieldVerify;
import io.github.wrobezin.framework.validator.annotation.StringRegexSatisfy;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuan
 * date: 2019/12/16
 */
@Data
@AllArgsConstructor
public class Student {
    @StringRegexSatisfy(value = "田所.*", invalidMessage = "你不是田所家的人")
    private String name;

    @IntegerValueSatisfy(min = 24, max = 24, invalidMessage = "你不是24岁")
    private Integer age;

    @NotNullable("test不能为空")
    @ObjectFieldVerify(TestEntity.class)
    private TestEntity test;

    @ArraySizeSatisfy(max = 2, invalidMessage = "你的爱好太多了")
    private String[] fancies;
}
