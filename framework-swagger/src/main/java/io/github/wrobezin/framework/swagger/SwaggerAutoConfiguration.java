package io.github.wrobezin.framework.swagger;

import io.github.wrobezin.framework.swagger.annotation.RegisterToSwagger;
import io.github.wrobezin.framework.swagger.property.SwaggerApplicationConfig;
import io.github.wrobezin.framework.utils.spring.PackageScanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Swagger自动扫包配置
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
public class SwaggerAutoConfiguration implements ApplicationContextAware {
    private final SwaggerApplicationConfig applicationConfig;
    private ConfigurableApplicationContext context;
    private Map<String, Set<Class<?>>> groups = new HashMap<>(8);
    private ApiInfo apiInfo;

    public SwaggerAutoConfiguration(SwaggerApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
        this.initGroupMap();
        this.initApiInfo();
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
                            for (String gourpName : annotation.groupName()) {
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

    @Bean
    public void creatDocket() {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Docket.class);
        beanDefinitionBuilder.addConstructorArgValue(DocumentationType.SWAGGER_2);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        // 若不存在任何分组，则创建一个空的默认分组，否则Swagger会自动获取所有类
        if (groups.isEmpty()) {
            groups.put(RegisterToSwagger.DEFAULT_GROUP_NAME, new HashSet<>());
        }
        groups.forEach((name, classes) -> {
            beanFactory.registerBeanDefinition(name, beanDefinition);
            HashSet<String> handlerNames = classes.stream()
                    .map(this::getSwaggerHandlerGroupName)
                    .collect(Collectors.toCollection(HashSet::new));
            Docket docket = context.getBean(name, Docket.class);
            docket.groupName(name)
                    .select()
                    // 根据handler的groupName过滤
                    .apis(handler -> {
                        if (handler instanceof WebMvcRequestHandler) {
                            return handlerNames.contains(handler.groupName());
                        }
                        // CombinedRequestHandler不作过滤
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
