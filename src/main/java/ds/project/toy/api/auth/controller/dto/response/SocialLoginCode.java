package ds.project.toy.api.auth.controller.dto.response;

import lombok.Getter;

@Getter
public class SocialLoginCode {
    private String code;

    private SocialLoginCode(String code) {
        this.code = code;
    }
    public static SocialLoginCode of(String code) {
        return new SocialLoginCode(code);
    }
}
