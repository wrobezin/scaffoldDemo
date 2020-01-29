package io.github.wrobezin.framework.utils.http;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuan
 * date: 2020/1/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {
    @JSONField(ordinal = 1)
    private String url;
    @JSONField(ordinal = 2)
    private String protocal;
    @JSONField(ordinal = 3)
    private String host;
    @JSONField(ordinal = 4)
    private Integer port;
    @JSONField(ordinal = 5)
    private String path;
    @JSONField(ordinal = 6)
    private String paramString;
    @JSONField(ordinal = 7)
    private Map<String, String> paramMap;

    public static final UrlInfo BLANK = new UrlInfo("", "", "", 80, "", "", new HashMap<>(0));
}
