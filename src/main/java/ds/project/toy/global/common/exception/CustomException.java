package ds.project.toy.global.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public CustomException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(cause), cause);
        this.responseCode = responseCode;
    }
}