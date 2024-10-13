package ds.project.toy.domain.user.entity;

import static jakarta.persistence.EnumType.STRING;

import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity(name = "user_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    @Enumerated(STRING)
    private UserInfoRole role;
    @Column(name = "state")
    @Enumerated(STRING)
    private UserInfoState state;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private UserInfo(String nickname, String email, UserInfoRole role, UserInfoState state,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserInfo createGuest() {
        return UserInfo.builder()
            .role(UserInfoRole.ROLE_GUEST)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .state(UserInfoState.ACTIVE)
            .build();
    }
}
