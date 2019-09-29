package org.cay.play.springlock.lock;

import org.cay.play.springlock.util.SpringContextUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisDistributedLock {

    StringRedisTemplate stringRedisTemplate;
    DlockWatcher dlockWatcher;

    public RedisDistributedLock() {
        stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
    }

    static long pxtime = 10;
    static long DEFAULT_SLEEP_TIME = 100;

    public boolean tryLock(String key, String uuid) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, pxtime, TimeUnit.SECONDS)) {
            dlockWatcher = new DlockWatcher(uuid, key, uuid);
            dlockWatcher.start();
            return true;
        }
        return false;
    }

//    @Override
//    public boolean tryLockBlocked(String key, String uuid, long blockTime) throws InterruptedException {
//        while (blockTime > 0) {
//            if (stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, 5, TimeUnit.SECONDS)) {
//                return true;
//            }
//            blockTime -= DEFAULT_SLEEP_TIME;
//            Thread.sleep(DEFAULT_SLEEP_TIME);
//        }
//        return false;
//    }


    public boolean unlock(String key, String uuid) {

        String luaScript = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Long.class);
        List<String> lst = new ArrayList<>();
        lst.add(key);
        Long rtn = stringRedisTemplate.execute(redisScript, lst, uuid);
        if (rtn == 0) {
            return false;
        }
//        if (dlockWatcher.isAlive()){
//            dlockWatcher.interrupt();
//        }
        return true;

    }
}
