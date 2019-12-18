package io.github.wrobezin.framework.web.controller;

import io.github.wrobezin.framework.business.entity.RequestResult;
import io.github.wrobezin.framework.business.entity.Student;
import io.github.wrobezin.framework.business.entity.User;
import io.github.wrobezin.framework.common.check.ParameterCheck;
import io.github.wrobezin.framework.common.check.annotation.IntegerValueSatisfy;
import io.github.wrobezin.framework.common.check.annotation.ObjectFieldVerify;
import io.github.wrobezin.framework.common.check.string.StringTypeEnum;
import io.github.wrobezin.framework.common.check.annotation.StringLengthSatisfy;
import io.github.wrobezin.framework.common.check.annotation.StringTypeSatisfy;
import io.github.wrobezin.framework.common.request.annotation.PublicRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuan
 * date: 2019/12/16
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    @PublicRequest
    public String hello() {
        return "hello";
    }

    @GetMapping("/fuck")
    public String fuck() {
        return "fuck";
    }

    @PostMapping("/user")
    @ParameterCheck
    public RequestResult user(
            @StringLengthSatisfy(minLength = 1, maxLength = 16, invalidMessage = "用户名不能为空且不能超过16个字符") String username,
            @StringLengthSatisfy(minLength = 6, maxLength = 16, invalidMessage = "密码长度应为6至16位") @StringTypeSatisfy(type = StringTypeEnum.ASCII, invalidMessage = "密码只能包含字母、数字及常规字符") String password,
            @IntegerValueSatisfy(min = 0, max = 150, invalidMessage = "年龄取值错误") Integer age) {
        return RequestResult.succeedWithData(new User(username, password, age));
    }

    @PostMapping("/student")
    @ParameterCheck
    public RequestResult student(@RequestBody @ObjectFieldVerify(Student.class) Student student) {
        return RequestResult.succeedWithData(student);
    }
}
