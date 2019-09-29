package org.cay.play.springredis.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Mytest implements Serializable {
    Long id;
    String name;
    int age;
    String sex;
}
