package io.github.wrobezin.framework.example.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yuan
 * date: 2019/12/16
 */
@SpringBootApplication(scanBasePackages = "io.github.wrobezin.framework")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
