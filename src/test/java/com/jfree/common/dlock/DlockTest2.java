package com.jfree.common.dlock;

import org.junit.Test;

/**
 * author : Guo LiXiao
 * date : 2017-6-6  12:25
 */

public class DlockTest2 {

    @Test
    public void test1() throws InterruptedException {
        DLock dLock = new RedisDLock("101.200.131.11", 6379, "glx920616!@#");

        dLock.unlock("aa", "cc");

    }
}
