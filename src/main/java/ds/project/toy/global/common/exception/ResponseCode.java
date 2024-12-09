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
    EXPIRED_TOKEN("AUTH-ERR-004", "만료된 토큰입니다."),

    //USER
    NOTFOUND_USER("USER-ERR-001", "유저를 찾을 수 없습니다."),
    NICKNAME_ALREADY_IN_USE("USER-ERR-002", "유저가 현재 사용 중인 닉네임입니다."),
    DUPLICATE_NICKNAME("USER-ERR-003", "이미 존재하는 닉네임입니다."),

    //ADMIN
    NOTFOUND_ADMIN("ADMIN-ERR-001", "존재하지 않은 관리자입니다."),
    FAILED_ADMIN_LOGIN("ADMIN-ERR-002", "아이디 또는 비밀번호가 잘못 되었습니다."),

    //PRODUCT
    MAX_IMAGE_COUNT_EXCEEDED("PRODUCT-ERR-001", "이미지 개수는 10개를 초과할 수 없습니다."),
    NOT_FOUND_CATEGORY("PRODUCT-ERR-002", "제품 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT("PRODUCT-ERR-003", "제품을 찾을 수 없습니다."),
    NOT_FOUND_INTEREST_PRODUCT("PRODUCT-ERR-004", "관심 제품을 찾을 수 없습니다."),

    //GLOBAL
    BAD_REQUEST("GLB-ERR-001", "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED("GLB-ERR-002", "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR("GLB-ERR-003", "내부 서버 오류입니다."),
    NOT_IMAGE("GLB-ERR-004", "이미지 파일이 아닙니다.");
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.message + " - " + e.getMessage();
    }

    public String getMessage(String additionalMessage) {
        return Optional.ofNullable(additionalMessage)
            .filter(Predicate.not(String::isBlank))
            .map(msg -> this.message + " - " + msg)
            .orElse(this.message);
    }
}
