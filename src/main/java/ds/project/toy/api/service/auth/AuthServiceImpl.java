package ds.project.toy.api.service.auth;

import ds.project.toy.api.service.auth.dto.ReissuedTokenServiceDto;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import ds.project.toy.global.common.vo.RedisPrefix;
import ds.project.toy.global.config.security.jwt.JwtTokenProvider;
import ds.project.toy.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Override
    public AuthToken reissuedToken(ReissuedTokenServiceDto dto) {
        if (!redisUtil.hasKey(RedisPrefix.TOKEN, dto.getRefreshToken())) {
            throw new CustomException(ResponseCode.INVALID_TOKEN);
        }
        String userName = jwtTokenProvider.getUserIdFromToken(
            dto.getRefreshToken());
        redisUtil.deleteValue(RedisPrefix.TOKEN, dto.getRefreshToken());
        return jwtTokenProvider.createTokenAndStore(userName);
    }
}
