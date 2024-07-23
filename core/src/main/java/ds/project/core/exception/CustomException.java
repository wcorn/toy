package ds.project.core.exception;


public class CustomException extends RuntimeException {

    public CustomException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public CustomException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public CustomException(String message, Throwable cause, ResponseCode responseCode) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    public CustomException(Throwable cause, ResponseCode responseCode) {
        super(cause);
        this.responseCode = responseCode;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace, ResponseCode responseCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    private final ResponseCode responseCode;
}