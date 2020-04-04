package io.github.wrobezin.framework.utils.spring;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 用于获取Spring上下文，或用于动态地注册、获取bean
 * 可以在不被Spring管理的类中使用，但整个程序需要Spring环境
 *
 * @author yuan
 * date: 2019/12/26
 */
@Component
public class BeanHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static final int RANDOM_BEAN_NAME_LENGTH = 6;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanHelper.applicationContext = applicationContext;
    }

    public Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public <T> T getBean(String name, Class<T> tClass) {
        return applicationContext.getBean(name, tClass);
    }

    public void registerBean(Class<?> beanClass, String name, Object... constructorArgs) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        for (Object arg : constructorArgs) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        String beanName = Optional.ofNullable(name).orElse(beanClass.getName() + "$" + RandomStringUtils.randomAlphanumeric(RANDOM_BEAN_NAME_LENGTH));
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
