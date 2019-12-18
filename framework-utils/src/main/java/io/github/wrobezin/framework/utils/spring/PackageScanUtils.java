package io.github.wrobezin.framework.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuan
 * date: 2019/12/18
 */
@Slf4j
@Component
public final class PackageScanUtils {
    private static final String CLASS_PATTERN = "/**/*.class";

    public static List<Class<?>> classScan(String packagePath) {
        List<Class<?>> result = new ArrayList<>();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(packagePath)
                    + CLASS_PATTERN;
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    result.add(Class.forName(className));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.toString());
        }
        return result;
    }
}
