package io.github.wrobezin.framework.swagger.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

/**
 * @author yuan
 * date: 2019/12/20
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerApplicationConfig {
    /** 扫包路径，将会扫描此路径下带{@link io.github.wrobezin.framework.swagger.annotation.RegisterToSwagger}注解的类 */
    private String basePackagePath;

    /** 对各个分组的路径过滤正则，若不设置则默认匹配任何串 */
    private Map<String, String> pathRegex;

    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private String version;
    private String contactName;
    private String contactUrl;
    private String contactEmail;

    /** 暂不支持 */
    private String vendorExtensionsJson;

    private String setVendorExtensionsJson(String vendorExtensionsJson) {
        throw new UnsupportedOperationException();
    }
}
