package io.github.wrobezin.framework.swagger;

import io.github.wrobezin.framework.swagger.annotation.RegisterToSwagger;
import io.github.wrobezin.framework.swagger.property.SwaggerApplicationConfig;
import io.github.wrobezin.framework.utils.spring.BeanHelper;
import io.github.wrobezin.framework.utils.spring.PackageScanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Swagger自动扫包配置
 * 针对Swagger2.9.2
 * ApiInfo从启动类所在模块的配置文件读取
 * 扫包根据Controller类上的{@link RegisterToSwagger}注解注册到相应分组
 *
 * @author yuan
 * date: 2019/12/19
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(value = SwaggerApplicationConfig.class)
@Slf4j
public class SwaggerAutoConfiguration {
    private final SwaggerApplicationConfig applicationConfig;
    private Map<String, Set<Class<?>>> groups = new HashMap<>(8);
    private ApiInfo apiInfo;
    private final BeanHelper beanHelper;

    public SwaggerAutoConfiguration(SwaggerApplicationConfig applicationConfig, BeanHelper beanHelper) {
        this.applicationConfig = applicationConfig;
        this.initGroupMap();
        this.initApiInfo();
        this.beanHelper = beanHelper;
    }

    /**
     * 扫包获取被{@link RegisterToSwagger}标记的类
     * 按照组名对这些类进行分组
     */
    private void initGroupMap() {
        Optional.ofNullable(applicationConfig)
                .map(SwaggerApplicationConfig::getBasePackagePath)
                .ifPresent(path -> PackageScanUtils.classScan(path)
                        .stream()
                        .filter(c -> c.isAnnotationPresent(RegisterToSwagger.class))
                        .forEach(c -> {
                            RegisterToSwagger annotation = AnnotationUtils.findAnnotation(c, RegisterToSwagger.class);
                            for (String gourpName : Optional.ofNullable(annotation).map(RegisterToSwagger::groupNames).orElse(new String[0])) {
                                if (groups.containsKey(gourpName)) {
                                    groups.get(gourpName).add(c);
                                } else {
                                    Set<Class<?>> classes = new HashSet<>();
                                    classes.add(c);
                                    groups.put(gourpName, classes);
                                }
                            }
                        }));
    }

    /**
     * 根据配置文件创建ApiInfo
     * TODO 添加VendorExtension支持
     */
    private void initApiInfo() {
        this.apiInfo = Optional.ofNullable(applicationConfig)
                .map(config -> new ApiInfoBuilder()
                        .title(config.getTitle())
                        .description(config.getDescription())
                        .version(config.getVersion())
                        .license(config.getLicense())
                        .licenseUrl(config.getLicenseUrl())
                        .contact(new Contact(
                                config.getContactName(),
                                config.getContactUrl(),
                                config.getContactEmail()))
                        .build())
                .orElse(new ApiInfoBuilder().build());
    }

    /**
     * @param clazz 字节码
     * @return 对应Swagger中WebMvcRequestHandler形式的groupName
     * @see WebMvcRequestHandler#groupName()
     */
    private String getSwaggerHandlerGroupName(Class<?> clazz) {
        return Paths.splitCamelCase(clazz.getSimpleName(), "-").replace("/", "").toLowerCase();
    }

    /**
     * 生成Docket
     * TODO 添加认证支持
     */
    @Bean
    public void creatDocket() {
        // 若不存在任何分组，则创建一个空的默认分组，否则Swagger会自动获取所有类
        if (groups.isEmpty()) {
            groups.put(RegisterToSwagger.DEFAULT_GROUP_NAME, new HashSet<>());
        }
        groups.forEach((name, classes) -> {
            // 注册Docket到Spring
            beanHelper.registerBean(Docket.class, name, DocumentationType.SWAGGER_2);
            // 获取Docket引用
            Docket docket = beanHelper.getBean(name, Docket.class);
            HashSet<String> handlerNames = classes.stream()
                    .map(this::getSwaggerHandlerGroupName)
                    .collect(Collectors.toCollection(HashSet::new));
            docket.apiInfo(this.apiInfo)
                    .groupName(name)
                    .select()
                    // 根据handler的groupName过滤
                    .apis(handler -> {
                        if (handler instanceof WebMvcRequestHandler) {
                            return handlerNames.contains(handler.groupName());
                        }
                        // TODO 暂时未对CombinedRequestHandler作过滤，后续添加
                        return true;
                    })
                    // 根据配置文件中对应分组的正则表达式进行过滤
                    .paths(Optional.ofNullable(applicationConfig.getPathRegex())
                            .map(regexs -> regexs.get(name))
                            .map(PathSelectors::regex)
                            .orElse(PathSelectors.any()))
                    .build();
        });
    }
}
