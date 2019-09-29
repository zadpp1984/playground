package org.cay.play.springlock.thread;

import lombok.extern.slf4j.Slf4j;
import org.cay.play.springlock.lock.RedisDistributedLock;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class MyThread extends Thread {

    private RedisDistributedLock redisDistributedLock;

    private String threadName;

    private MyThread() {
    }

    public MyThread(String threadName) {
        this.threadName = threadName;
        mylog("Create Thread:" + threadName);
        redisDistributedLock = new RedisDistributedLock();
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    void mylog(String str) {
        log.debug("Thread [" + threadName + "] " + format.format(new Date()) + ": " + str);
    }

    @Override
    public void run() {
        mylog("run start!");
        boolean locked = redisDistributedLock.tryLock("dlock", threadName);
        if (locked) {
            try {
                mylog("get the lock!");
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                boolean unlockflg = redisDistributedLock.unlock("dlock", threadName);
                if (unlockflg) {
                    mylog("unlock successed!");
                } else {
                    mylog("unlock failed!");
                }
            }
        }
        mylog("run stop!");
    }


    @Override
    public synchronized void start() {
        mylog("Thread [" + threadName + "] is start!!");
        super.start();
        mylog("Thread [" + threadName + "] is start over!");
    }
}
