package ds.project.toy.api.service.auth;

import ds.project.toy.api.service.auth.dto.ReissuedTokenServiceDto;
import ds.project.toy.global.common.vo.AuthToken;

public interface AuthService {

    AuthToken reissuedToken(ReissuedTokenServiceDto dto);
}
