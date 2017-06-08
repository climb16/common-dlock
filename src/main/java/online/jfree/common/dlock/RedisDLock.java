package online.jfree.common.dlock;

import redis.clients.jedis.Jedis;


/**
 * 分布式锁redis实现
 * author : Guo LiXiao
 * date : 2017-5-12  13:49
 *
 * @since 2.0
 */

public final class RedisDLock extends AbstractDLock {

    private Jedis jedis;

    private int expire = 180;

    public RedisDLock() {
    }

    public RedisDLock(String host, int port) {
        this(host, port, null);
    }

    public RedisDLock(String host, int port, String password) {
        try {
            jedis = new Jedis(host, port);
            if (password != null && password.trim().length() > 0) {
                jedis.auth(password);
            }
        } catch (Exception e) {
            logger.error("创建jedis失败", e);
        }
    }

    public RedisDLock(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void lock(String key, String holder) {
        try {
            blokDlock(key, holder, 0);
        } catch (TimeoutException e) {
        }
    }

    @Override
    public void lock(String key, String holder, long timeout) throws TimeoutException {
        blokDlock(key, holder, timeout);
    }

    @Override
    public void unlock(String key, String holder) {
        if (this.jedis == null) {
            logger.error("dlock jedis is null");
            return;
        }
        Lock lock = removeLock(LOCK_PREFIX + key, holder, 0, expire);
        jedis.watch(lock.getKey());
        if (holder.equals(jedis.get(lock.getKey()))) {
            jedis.del(lock.getKey());
        }
        jedis.unwatch();
    }

    private void blokDlock(String key, String holder, long timeout) throws TimeoutException {
        if (this.jedis == null) {
            logger.error("dlock jedis is null");
            return;
        }
        Lock lock = putLock(LOCK_PREFIX + key, holder, timeout, expire);
        if (lock.getHolder().equals(jedis.get(lock.getKey()))) return;
        ThreadLocal<Boolean> blocked = new ThreadLocal<>();
        blocked.set(false);
        ThreadLocal<Long> start = new ThreadLocal<>();
        start.set(System.currentTimeMillis());
        while (!blocked.get()){
            synchronized (lock) {
                if (jedis.setnx(LOCK_PREFIX + key, holder) == 1) {
                    //设置锁强制过期时间
                    jedis.expire(lock.getKey(), lock.getExpire() > 0 ? lock.getExpire() : expire);
                    return;
                }
                try {
                    lock.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                blocked.set(timeout > 0 && start.get() + timeout > System.currentTimeMillis());
            }
        }
        throw new TimeoutException("block time out");
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

}