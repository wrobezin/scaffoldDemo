package io.github.wrobezin.framework.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yuan
 * date: 2019/12/16
 */
@SpringBootApplication
@ComponentScan({"io.github.wrobezin.framework.common", "io.github.wrobezin.framework.business", "io.github.wrobezin.framework.web"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
