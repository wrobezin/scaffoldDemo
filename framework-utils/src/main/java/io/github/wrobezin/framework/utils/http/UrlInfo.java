package io.github.wrobezin.framework.utils.http;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 绝对路径URL信息
 *
 * @author yuan
 * date: 2020/1/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlInfo {
    @JSONField(ordinal = 1)
    private String protocal;
    @JSONField(ordinal = 2)
    private String host;
    @JSONField(ordinal = 3)
    private Integer port;
    @JSONField(ordinal = 4)
    private String path;
    @JSONField(ordinal = 5)
    private String queryString;
    @JSONField(ordinal = 6)
    private Map<String, String> queryMap;
    @JSONField(ordinal = 7)
    private String fragment;

    public String getBaseUrl() {
        return getProtocal() + "://" + getHost() + ":" + getPort() + getPath();
    }

    public String getUrlWithQuery() {
        String baseUrl = getBaseUrl();
        String queryString = getQueryString();
        if (!StringUtils.isBlank(queryString)) {
            baseUrl += "?" + queryString;
        }
        return baseUrl;
    }

    public static final UrlInfo BLANK = UrlInfo.builder()
            .protocal("")
            .host("")
            .port(80)
            .path("")
            .queryString("")
            .queryMap(new HashMap<>(0))
            .fragment("")
            .build();

    public boolean isHttpUrl() {
        return this.getProtocal().startsWith("http");
    }
}
