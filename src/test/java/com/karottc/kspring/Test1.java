package com.karottc.kspring;

import com.custom.AService;

/**
 * @author
 * @date 2023-07-21 17:40
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }
}
