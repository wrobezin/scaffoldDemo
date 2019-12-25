package io.github.wrobezin.framework.example.swagger.controller;

import io.github.wrobezin.framework.swagger.annotation.RegisterToSwagger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:siyuan_liang@sui.com">siyuan_liang</a>
 * date: 2019/12/25
 */
@RestController
@RequestMapping("/hahaha")
@RegisterToSwagger
public class SwaggerDefaultExampleController {
    @GetMapping("/")
    public String hahaha() {
        return "hahaha";
    }
}
