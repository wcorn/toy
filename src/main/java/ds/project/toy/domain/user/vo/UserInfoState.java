package ds.project.toy.domain.user.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserInfoState {
    ACTIVE("활동"),
    WITHDRAWN("탈퇴"),
    BAN("정지");
    private final String name;
}
