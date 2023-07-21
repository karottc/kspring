package com.karottc.kspring;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2023-07-21 17:11
 */
public class ClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    // 读取外部配置，解析出bean 的定义
    public ClassPathXmlApplicationContext(String fileName) throws Exception {
        this.readXML(fileName);
        this.instanceBeans();
    }

    private void readXML(String fileName) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        Document document = saxReader.read(xmlPath);
        Element rootElement = document.getRootElement();
        // 依次处理 bean
        for (Element element : rootElement.elements()) {
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            beanDefinitions.add(beanDefinition);
        }
    }

    private void instanceBeans() throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            singletons.put(
                    beanDefinition.getId(),
                    Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance()
            );
        }
    }

    // 从容器中获取 bean 实例
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
