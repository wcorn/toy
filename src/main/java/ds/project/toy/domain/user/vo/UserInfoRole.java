package ds.project.toy.domain.user.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserInfoRole {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");
    private final String name;
}
