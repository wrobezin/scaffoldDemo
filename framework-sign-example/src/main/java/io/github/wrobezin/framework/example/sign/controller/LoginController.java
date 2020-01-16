package io.github.wrobezin.framework.example.sign.controller;

import io.github.wrobezin.framework.example.sign.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Optional;

/**
 * @author yuan
 * date: 2019/12/26
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    @ResponseBody
    public String login(@RequestBody User user) {
        // 用户名和密码非空且相同则成功
        return Optional.ofNullable(user)
                .filter(u -> !StringUtils.isEmpty(u.getUsername()))
                .filter(u -> u.getUsername().equals(u.getPassword()))
                .map(u -> "成功")
                .orElse("失败");
    }
}
