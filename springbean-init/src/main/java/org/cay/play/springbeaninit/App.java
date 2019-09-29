package org.cay.play.springbeaninit;

import org.cay.play.springbeaninit.bean.InitTestBean1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        InitTestBean1 initTestBean1 = applicationContext.getBean(InitTestBean1.class);
        System.out.println(initTestBean1.toString());
    }
}
