package org.cay.play.springlock.lock;

import lombok.extern.slf4j.Slf4j;
import org.cay.play.springlock.util.SpringContextUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class DlockWatcher extends Thread {

    StringRedisTemplate stringRedisTemplate;
    String threadName;
    String key;
    String value;

    private DlockWatcher() {

    }

    public DlockWatcher(String threadName, String key, String value) {
        this.threadName = threadName;
        this.key = key;
        this.value = value;
        stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
    }

    boolean runflg = true;

    public void stopWatch() {
        runflg = false;
    }

    String luaScript = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end";

    @Override
    public void run() {
        mylog("watcher begin!");
        while (runflg) {
            try {
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
                redisScript.setScriptText(luaScript);
                redisScript.setResultType(Long.class);
                List<String> lst = new ArrayList<>();
                lst.add(key);
                Long rtn = stringRedisTemplate.execute(redisScript, lst, value, "10");
                if (rtn == 0) {
                    break;
                } else {
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        mylog("watcher end!");
    }


    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    void mylog(String str) {
        log.debug("Watcher Thread [" + threadName + "] " + format.format(new Date()) + ": " + str);
    }

}
