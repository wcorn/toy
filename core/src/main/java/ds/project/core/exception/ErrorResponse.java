package ds.project.core.exception;



public class ErrorResponse {

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private final String code;
    private final String message;

    public static ErrorResponse of(ResponseCode code) {
        return new ErrorResponse(code.getCode(), code.getMessage());
    }

    public static ErrorResponse of(ResponseCode code, Exception e) {
        return new ErrorResponse(code.getCode(), code.getMessage(e));
    }

    public static ErrorResponse of(ResponseCode code, String message) {
        return new ErrorResponse(code.getCode(), code.getMessage(message));
    }
}