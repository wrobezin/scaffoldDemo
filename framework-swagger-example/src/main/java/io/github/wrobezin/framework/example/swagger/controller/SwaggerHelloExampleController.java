package io.github.wrobezin.framework.example.swagger.controller;

import io.github.wrobezin.framework.example.swagger.entity.SwaggerExampleEntity;
import io.github.wrobezin.framework.swagger.annotation.RegisterToSwagger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:siyuan_liang@sui.com">siyuan_liang</a>
 * date: 2019/12/25
 */
@RestController
@RequestMapping("/swagger-example")
@RegisterToSwagger(groupNames = "hello")
public class SwaggerHelloExampleController {
    @GetMapping("/hello")
    public Object hello() {
        return "hello";
    }

    @GetMapping("/hello/{words}")
    public Object helloWithWord(@PathVariable String words) {
        return words;
    }

    @PostMapping("/hello")
    public Object helloPost(String username, String password) {
        return "helloPost " + username + " : " + password;
    }

    @PostMapping(value = "/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Object helloForm(@RequestParam String username, @RequestParam String password) {
        return "helloForm " + username + " : " + password;
    }

    @PostMapping("/entity")
    public Object helloEntity(SwaggerExampleEntity entity) {
        return entity;
    }
}
