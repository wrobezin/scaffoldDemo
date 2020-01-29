package io.github.wrobezin.framework.utils.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yuan
 * date: 2020/1/21
 */
@Slf4j
public final class HttpUrlUtils {
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?)://([\\w.%!-]*)(?:[:]?([\\d]*))([\\w.%!/-]*)(?:[?]?(.*))");
    private static final int URL_PATTERN_PROTOCAL_GROUP_INDEX = 1;
    private static final int URL_PATTERN_HOST_GROUP_INDEX = 2;
    private static final int URL_PATTERN_PORT_GROUP_INDEX = 3;
    private static final int URL_PATTERN_PATH_GROUP_INDEX = 4;
    private static final int URL_PATTERN_PARAM_GROUP_INDEX = 5;

    public static String urlEncode(@NotNull final String url, @NotNull final String charSet) {
        try {
            return URLEncoder.encode(url, charSet)
                    .replace("%3A", ":")
                    .replace("%2F", "/")
                    .replace("%3F", "?")
                    .replace("%3D", "=")
                    .replace("%26", "&");
        } catch (UnsupportedEncodingException e) {
            log.error("URL编码出错 {} {}", url, charSet);
            return "";
        }
    }

    public static String urlDecode(@NotNull final String url, @NotNull final String charSet) {
        try {
            return URLDecoder.decode(url, charSet);
        } catch (UnsupportedEncodingException e) {
            log.error("URL解码出错 {} {}", url, charSet);
            return "";
        }
    }

    public static String urlEncode(@NotNull final String url) {
        return urlEncode(url, "UTF-8");
    }

    public static String urlDecode(@NotNull final String url) {
        return urlDecode(url, "UTF-8");
    }

    public static UrlInfo parseUrl(final String url) {
        return Optional.ofNullable(url)
                .map(HttpUrlUtils::urlEncode)
                .map(URL_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> {
                    String protocal = matcher.group(URL_PATTERN_PROTOCAL_GROUP_INDEX);
                    String host = matcher.group(URL_PATTERN_HOST_GROUP_INDEX);
                    String port = matcher.group(URL_PATTERN_PORT_GROUP_INDEX);
                    String path = matcher.group(URL_PATTERN_PATH_GROUP_INDEX);
                    String paramString = matcher.group(URL_PATTERN_PARAM_GROUP_INDEX);
                    LinkedHashMap<String, String> paramMap = StringUtils.isBlank(paramString)
                            ? new LinkedHashMap<>(0)
                            : Arrays.stream(paramString.split("&")).collect(
                            LinkedHashMap::new,
                            (map, string) -> {
                                String[] kv = string.split("=");
                                map.put(urlDecode(kv[0]), kv.length > 1 ? urlDecode(kv[1]) : "");
                            },
                            LinkedHashMap::putAll
                    );
                    return new UrlInfo(url, protocal, host, StringUtils.isBlank(port) ? 80 : Integer.parseInt(port), urlDecode(path), urlDecode(paramString), paramMap);
                }).orElse(UrlInfo.BLANK);
    }

    public static void main(String[] args) {
        Arrays.asList(
                "http://t.test.com:80/a-b/123",
                "https://t.test.com/a-b/123?fuck=shit&a=0.88&b=c",
                "http://test.com/中文测试?中文=测试&eunha=银河",
                "http://test.com/?没有值",
                "http://test.com/?没有值&mmp=sb"
        ).forEach(url -> System.out.println(JSON.toJSONString(parseUrl(url))));
    }
}
