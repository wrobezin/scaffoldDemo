package io.github.wrobezin.framework.example.validator.controller;

import io.github.wrobezin.framework.example.validator.entity.RequestResult;
import io.github.wrobezin.framework.example.validator.entity.Student;
import io.github.wrobezin.framework.example.validator.entity.User;
import io.github.wrobezin.framework.validator.annotation.BigDecimalValueSatisfy;
import io.github.wrobezin.framework.validator.annotation.IntegerValueSatisfy;
import io.github.wrobezin.framework.validator.annotation.ObjectFieldVerify;
import io.github.wrobezin.framework.validator.annotation.ParameterCheck;
import io.github.wrobezin.framework.validator.annotation.StringLengthSatisfy;
import io.github.wrobezin.framework.validator.annotation.StringTypeSatisfy;
import io.github.wrobezin.framework.validator.string.StringTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

/**
 * @author yuan
 * date: 2019/12/16
 */
@RestController
@RequestMapping("/validation")
public class ValidatorUseController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/user")
    @ParameterCheck
    public RequestResult user(
            @StringLengthSatisfy(minLength = 1, maxLength = 16, invalidMessage = "用户名不能为空且不能超过16个字符") String username,
            @StringLengthSatisfy(minLength = 6, maxLength = 16, invalidMessage = "密码长度应为6至16位") @StringTypeSatisfy(type = StringTypeEnum.ASCII, invalidMessage = "密码只能包含字母、数字及常规符号") String password,
            @IntegerValueSatisfy(min = 0, max = 150, invalidMessage = "年龄取值错误") Integer age) {
        return RequestResult.succeedWithData(new User(username, password, age));
    }

    @PostMapping("/student")
    @ParameterCheck
    public RequestResult student(@RequestBody @ObjectFieldVerify(Student.class) Student student) {
        return RequestResult.succeedWithData(student);
    }

    @PostMapping("/big-decimal")
    @ParameterCheck
    public RequestResult bigDecimal(@BigDecimalValueSatisfy(min = "50", max = "666666666666666666666666666666666666666666666", invalidMessage = "范围不对") BigDecimal bigDecimal) {
        return RequestResult.succeedWithData(bigDecimal);
    }
}
