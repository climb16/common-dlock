package online.jfree.common.dlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author : Guo LiXiao
 * date : 2017-6-6  17:51
 */

public abstract class AbstractDLock implements DLock {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Map<Integer, Lock> locks = new ConcurrentHashMap<>();

    protected Lock putLock(String key, String holder, long timeout, int expire){
        Lock lock = new Lock(key, holder, timeout, expire);
        int hashCode = lock.hashCode();
        synchronized (locks){
            if (locks.get(hashCode) == null){
                locks.put(hashCode, lock);
            } else {
                lock = locks.get(hashCode);
            }
        }
        return lock;
    }

    protected Lock removeLock(String key, String holder, long timeout, int expire){
        Lock lock = new Lock(key, holder, timeout, expire);
        int hashCode = lock.hashCode();
        synchronized (locks){
            if (locks.get(hashCode) != null){
                locks.remove(hashCode);
            }
        }
        return lock;
    }

    protected final class Lock{
        private String key;
        private String holder;
        private long timeout;
        private int expire;

        protected Lock(String key, String holder, long timeout, int expire) {
            this.key = key;
            this.holder = holder;
            this.timeout = timeout;
            this.expire = expire;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getHolder() {
            return holder;
        }

        public void setHolder(String holder) {
            this.holder = holder;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lock lock = (Lock) o;
            if (key != null ? !key.equals(lock.key) : lock.key != null) return false;
            return holder != null ? holder.equals(lock.holder) : lock.holder == null;
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (holder != null ? holder.hashCode() : 0);
            return result;
        }
    }
}