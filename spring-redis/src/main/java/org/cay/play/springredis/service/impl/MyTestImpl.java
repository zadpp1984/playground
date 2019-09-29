package org.cay.play.springredis.service.impl;

import org.cay.play.springredis.bean.Mytest;
import org.cay.play.springredis.mapper.MyTestDao;
import org.cay.play.springredis.service.IMyTest;
import org.cay.play.systemlog.SystemServiceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "test")
public class MyTestImpl implements IMyTest {

    @Autowired
    MyTestDao myTestDao;

    //    @CachePut(key = "#result.id")
    @Override
    @Cacheable(key = "#p0", value = "users", unless = "#result == null")
    @SystemServiceLog(description = "service test!")
    public Mytest getUserById(Long id) {
        return myTestDao.getMytestById(id);
    }


    @Override
    @CacheEvict(key = "#p0.id", value = "users")
    @Transactional
    public boolean updateById(Mytest mytest) {
        int rtn = myTestDao.updateById(mytest);
        if (rtn != 1) {
            return false;
        }
        return true;
    }
}
