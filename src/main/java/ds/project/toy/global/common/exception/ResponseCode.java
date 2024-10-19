package ds.project.toy.global.common.exception;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    //AUTH
    UNAUTHORIZED("AUTH-ERR-001", "인증되지 않은 사용자입니다."),
    INVALID_TOKEN("AUTH-ERR-002", "유효한 토큰이 아닙니다."),
    UNSUPPORTED_TOKEN("AUTH-ERR-003", "지원하지 않는 토큰입니다."),
    EXPIRED_TOKEN("AUTH-ERR-004","만료된 토큰입니다."),
    //GLOBAL
    BAD_REQUEST("GLB-ERR-001", "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED("GLB-ERR-002", "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR("GLB-ERR-003", "내부 서버 오류입니다.");
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
