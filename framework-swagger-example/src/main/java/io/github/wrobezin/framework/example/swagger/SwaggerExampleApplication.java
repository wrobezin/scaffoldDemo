package io.github.wrobezin.framework.example.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yuan
 * date: 2019/12/25
 */
@SpringBootApplication(scanBasePackages = "io.github.wrobezin.framework")
public class SwaggerExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerExampleApplication.class, args);
    }
}
