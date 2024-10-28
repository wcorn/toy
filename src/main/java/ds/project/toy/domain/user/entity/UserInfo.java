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
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

@Entity(name = "user_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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
    @Column(name = "profile_image")
    private String profileImage;
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
        String profileImage, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.state = state;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserInfo(UserInfo userInfo) {
        this.userId = userInfo.getUserId();
        this.nickname = userInfo.getNickname();
        this.email = userInfo.getEmail();
        this.role = userInfo.getRole();
        this.state = userInfo.getState();
        this.profileImage = userInfo.getProfileImage();
        this.createdAt = userInfo.getCreatedAt();
        this.updatedAt = userInfo.getUpdatedAt();
    }

    public static UserInfo creatUser(String email, String nickname) {
        return UserInfo.builder()
            .email(email)
            .nickname(nickname)
            .role(UserInfoRole.ROLE_USER)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .state(UserInfoState.ACTIVE)
            .build();
    }

    public static UserInfo of(String nickname, String email,
        String profileImage, UserInfoRole role, UserInfoState state) {
        return UserInfo.builder()
            .email(email)
            .nickname(nickname)
            .profileImage(profileImage)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .role(role)
            .state(state)
            .build();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
