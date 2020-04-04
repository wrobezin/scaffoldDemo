package io.github.wrobezin.framework.utils.spring;

import io.github.wrobezin.framework.utils.spring.useless.A;
import io.github.wrobezin.framework.utils.spring.useless.B;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import java.util.List;

/**
 * @author yuan
 * date: 2019/12/26
 */
@SpringBootTest
@SpringBootApplication
@Slf4j
public class SpringUtilTest {
    @Autowired
    private BeanHelper beanHelper;

    @Test
    void packageScanUtilsTest() {
        System.out.println("----------扫包工具类测试开始----------");
        List<Class<?>> classes = PackageScanUtils.classScan("io.github.wrobezin.framework.utils");
        Assert.isTrue(classes.size() > 0, "扫包工具类返回结果为空列表");
        // 这里打印看看扫描结果就好，不写断言了
        classes.forEach(System.out::println);
        System.out.println("----------扫包工具类测试结束----------");
    }

    @Test
    void beanUtilsTest() {
        Assert.notNull(beanHelper.getApplicationContext(), "BeanUtils中ApplicationContext为null");
        Assert.notNull(beanHelper.getBean(A.class), "获取A类Bean失败");
        final String message = "测试";
        final String beanName = "b";
        beanHelper.registerBean(B.class, beanName, message);
        B bean = beanHelper.getBean(B.class);
        Assert.notNull(bean, "注册B类Bean失败");
        System.out.println("B.message：" + bean.getMessage());
        Assert.isTrue(message.equals(bean.getMessage()), "B类Bean注册时构造有误");
        Assert.notNull(beanHelper.getBean(beanName), "通过名称获取Bean失败");
    }
}
