package ds.project.core.exception;

import java.util.Optional;
import java.util.function.Predicate;

public enum ResponseCode {
    //GLOBAL
    BAD_REQUEST("GLB-ERR-001",  "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED("GLB-ERR-002", "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR("GLB-ERR-003", "내부 서버 오류입니다.");

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;


    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
