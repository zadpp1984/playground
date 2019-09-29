package org.cay.play.springlock;

import org.cay.play.springlock.thread.MyThread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLockApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringLockApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyThread myThread = new MyThread("T1");
        MyThread myThread2 = new MyThread("T2");
        myThread.start();
        myThread2.start();
    }
}
