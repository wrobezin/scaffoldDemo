package io.github.wrobezin.framework.utils.http;

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
import java.util.stream.Stream;

/**
 * TODO 处理相对路径
 *
 * @author yuan
 * date: 2020/1/21
 */
@Slf4j
public final class HttpUrlUtils {
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?)://([\\w.%!-]*)(?::?([\\d]*))([\\w.%!/-]*)(?:\\?([\\w.%!=&-]*))?(?:#(.*))?");
    private static final int URL_PATTERN_PROTOCOL_GROUP_INDEX = 1;
    private static final int URL_PATTERN_HOST_GROUP_INDEX = 2;
    private static final int URL_PATTERN_PORT_GROUP_INDEX = 3;
    private static final int URL_PATTERN_PATH_GROUP_INDEX = 4;
    private static final int URL_PATTERN_QUERY_GROUP_INDEX = 5;
    private static final int URL_PATTERN_FRAGMENT_GROUP_INDEX = 6;

    public static String urlEncode(@NotNull final String url, @NotNull final String charSet) {
        try {
            return URLEncoder.encode(url, charSet)
                    .replace("%3A", ":")
                    .replace("%2F", "/")
                    .replace("%3F", "?")
                    .replace("%3D", "=")
                    .replace("%23", "#")
                    .replace("%26", "&");
        } catch (UnsupportedEncodingException e) {
            log.error("URL编码出错 {} {}", url, charSet);
            return "";
        }
    }

    public static String urlDecode(@NotNull final String encodedString, @NotNull final String charSet) {
        return Optional.ofNullable(encodedString)
                .map(str -> {
                    try {
                        return URLDecoder.decode(str, charSet);
                    } catch (UnsupportedEncodingException e) {
                        log.error("URL解码出错 {} {}", encodedString, charSet);
                        return "";
                    }
                })
                .orElse("");
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
                    String protocal = matcher.group(URL_PATTERN_PROTOCOL_GROUP_INDEX);
                    String host = urlDecode(matcher.group(URL_PATTERN_HOST_GROUP_INDEX));
                    String port = urlDecode(matcher.group(URL_PATTERN_PORT_GROUP_INDEX));
                    String path = urlDecode(matcher.group(URL_PATTERN_PATH_GROUP_INDEX));
                    String query = urlDecode(matcher.group(URL_PATTERN_QUERY_GROUP_INDEX));
                    String fragment = urlDecode(matcher.group(URL_PATTERN_FRAGMENT_GROUP_INDEX));
                    LinkedHashMap<String, String> queryMap = StringUtils.isBlank(query)
                            ? new LinkedHashMap<>(0)
                            : Arrays.stream(query.split("&")).collect(
                            LinkedHashMap::new,
                            (map, string) -> {
                                String[] kv = string.split("=");
                                map.put(kv[0], kv.length > 1 ? kv[1] : "");
                            },
                            LinkedHashMap::putAll
                    );
                    return UrlInfo.builder()
                            .protocal(protocal)
                            .host(host)
                            .port(StringUtils.isBlank(port) ? 80 : Integer.parseInt(port))
                            .path(path)
                            .queryString(query)
                            .queryMap(queryMap)
                            .fragment(fragment)
                            .build();
                }).orElse(UrlInfo.BLANK);
    }

    public static boolean hasSameHost(String url1, String url2) {
        return parseUrl(url1).getHost().equals(parseUrl(url2).getHost());
    }

    public static void main(String[] args) {
        Stream.of(
                "http://t.test.com:80/a-b/123",
                "https://t.test.com/a-b/123?fuck=shit&a=0.88&b=c",
                "http://test.com/中文测试?中文=测试&eunha=银河",
                "http://test.com/?没有值",
                "http://test.com/?没有值&mmp=sb",
                "http://test.com/?没有值&mmp=sb#aaa",
                "http://jiaren.org:80/2013/03/17/youyuan-4/comment-page-1/?q=qq#comment-47149",
                "http://jiaren.org:80/2013/03/17/youyuan-4/comment-page-1/#comment-37820")
                .map(HttpUrlUtils::parseUrl)
                .forEach(url -> {
                    System.out.println(url);
                    System.out.println(url.getBaseUrl());
                    System.out.println(url.getUrlWithQuery());
                    System.out.println("------------------------");
                });
    }
}
