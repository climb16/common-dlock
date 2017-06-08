package online.jfree.common.dlock;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016-10-29 17:58
 */
public class TimeoutException extends DLockException {

    public TimeoutException() {
        super();
    }

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }

    protected TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
