package ds.project.toy.api.controller.admin.dto.request;

import ds.project.toy.api.service.admin.dto.AdminLoginServiceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLoginRequest {

    @Schema(description = "아이디", defaultValue = "dsk0820")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;
    @Schema(description = "비밀번호", defaultValue = "pssword123!")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private AdminLoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public static AdminLoginRequest of(String id, String password) {
        return AdminLoginRequest.builder()
            .id(id)
            .password(password)
            .build();
    }

    public AdminLoginServiceDto toServiceDto() {
        return AdminLoginServiceDto.of(this.id, this.password);
    }
}
