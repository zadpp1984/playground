package org.cay.play.springredis.service;


import org.cay.play.springredis.bean.Mytest;

public interface IMyTest {

    Mytest getUserById(Long id);

    boolean updateById(Mytest mytest);
}
