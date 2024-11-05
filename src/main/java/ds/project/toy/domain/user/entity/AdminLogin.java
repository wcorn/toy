package ds.project.toy.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "admin_login")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AdminLogin {

    @Id
    @Column(name = "admin_id")
    private Long adminId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
    private UserInfo userInfo;
    @Column(name = "id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "salt")
    private String salt;

    @Builder
    private AdminLogin(Long adminId, UserInfo userInfo, String id, String password, String salt) {
        this.adminId = adminId;
        this.userInfo = userInfo;
        this.id = id;
        this.password = password;
        this.salt = salt;
    }

    public static AdminLogin of(UserInfo userInfo, String id, String password, String salt) {
        return AdminLogin.builder()
            .userInfo(userInfo)
            .id(id)
            .password(password)
            .salt(salt)
            .build();
    }

}
