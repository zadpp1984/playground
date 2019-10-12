package org.cay.play.springtccconsumer;

import org.bytesoft.bytetcc.supports.springcloud.config.SpringCloudSecondaryConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import(SpringCloudSecondaryConfiguration.class)
@MapperScan("org.cay.play.springtccconsumer.consumer.dao")
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients("org.cay.play.springtccconsumer.feign")
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrix
public class SpringtccConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtccConsumerApplication.class, args);
    }

    private static BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory factory) throws BeansException {
        beanFactory = factory;
    }
}
