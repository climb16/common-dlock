package com.jfree.common.dlock;

import org.junit.Test;

import static java.lang.Thread.sleep;

/**
 * author : Guo LiXiao
 * date : 2017-6-6  10:27
 */

public class DlockTest {

    @Test
    public void test1() throws TimeoutException {
        final DLock dLock = new RedisDLock("101.200.131.11", 6379, "glx920616!@#");

        dLock.lock("aa", "aa");


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dLock.unlock("aa", "cc");
            }
        }).start();*/

    }
}