package online.jfree.common.dlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式锁
 * author : Guo LiXiao
 * date : 2017-5-12  12:05
 * @since 2.0
 */
public interface DLock {

    String LOCK_PREFIX = "LOCK_";

    Logger logger = LoggerFactory.getLogger(RedisDLock.class);

    /**
     * 尝试获取锁, 若获取不到则会一直阻塞，直到获取
     * @param key 锁id
     * @param holder 锁持有者
     */
    void lock(String key, String holder);

    /**
     * 尝试获取锁, 若获取不到则会一直阻塞，在 timeout时间内还没获取到，则抛出timeout异常
     * @param key  锁id
     * @param holder  锁持有者
     * @param timeout 超时时间
     * @throws TimeoutException timeout时间内仍未获取锁抛出 TimeoutException
     */
    void lock(String key, String holder, long timeout) throws TimeoutException;


    /**
     * 释放锁
     * @param key 锁ID
     * @param holder 所持有者，锁只能被当前持有者释放
     */
    void unlock(String key, String holder);
}
