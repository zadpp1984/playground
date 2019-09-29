package org.cay.play.springbeaninit.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * bean 初始化的方法
 */
//@Component
@Slf4j
public class InitTestBean1 implements InitializingBean {

    InitTestBean1() {
        log.debug("run InitTestBean1!");
    }


    void init_method1() {
        log.debug("run init_method1!");
    }

    @PostConstruct
    void postConstruct_method() {
        log.debug("run postConstruct_method!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("run afterPropertiesSet!");
    }


}
