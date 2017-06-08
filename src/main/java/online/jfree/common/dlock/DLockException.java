package online.jfree.common.dlock;

/**
 * author : Guo LiXiao
 * date : 2017-6-6  13:54
 */

public class DLockException extends Exception {

    public DLockException() {
        super();
    }

    public DLockException(String message) {
        super(message);
    }

    public DLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public DLockException(Throwable cause) {
        super(cause);
    }

    protected DLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
