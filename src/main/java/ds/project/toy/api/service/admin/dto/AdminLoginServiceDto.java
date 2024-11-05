package ds.project.toy.api.service.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLoginServiceDto {

    private String id;
    private String password;

    @Builder
    private AdminLoginServiceDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
    public static AdminLoginServiceDto of(String id, String password) {
        return AdminLoginServiceDto.builder()
            .id(id)
            .password(password).build();
    }
}
