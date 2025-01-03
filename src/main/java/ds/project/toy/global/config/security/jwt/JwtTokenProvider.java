package ds.project.toy.global.config.security.jwt;

import static io.jsonwebtoken.Jwts.builder;

import ds.project.toy.global.common.vo.AuthToken;
import ds.project.toy.global.common.vo.RedisPrefix;
import ds.project.toy.global.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JwtTokenProvider {

    private static final String TOKEN_TYPE = "token_type";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private final SecretKey secretKey;
    private final long accessTokenValidityInMinute;
    private final long refreshTokenValidityInDay;
    private final RedisUtil redisUtil;
    private final JwtParser jwtParser;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(
        @Value("${security.access-token-minute}") long accessTokenMinute,
        @Value("${security.refresh-token-day}") long refreshTokenDay,
        @Value("${security.key}") String secret, RedisUtil redisUtil,
        UserDetailsService userDetailsService) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenValidityInMinute = accessTokenMinute;
        this.refreshTokenValidityInDay = refreshTokenDay;
        this.redisUtil = redisUtil;
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
        this.userDetailsService = userDetailsService;
    }

    public AuthToken createTokenAndStore(String id) {
        LocalDateTime now = LocalDateTime.now();
        AuthToken authTokens = AuthToken.of(createAccessToken(id),
            createRefreshToken(now, id));
        redisUtil.setValueWithExpire(RedisPrefix.TOKEN,
            authTokens.getRefreshToken(),
            authTokens.getAccessToken(),
            Duration.ofDays(refreshTokenValidityInDay));
        return authTokens;
    }

    public Authentication getAuthentication(String token) {
        String usernameFromToken = getUserIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
        return new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
    }

    private String createAccessToken(String username) {
        LocalDateTime time = LocalDateTime.now();
        return builder()
            .subject(username)
            .claim(TOKEN_TYPE, ACCESS_TOKEN)
            .issuedAt(Timestamp.valueOf(time))
            .expiration(Timestamp.valueOf(time.plusMinutes(accessTokenValidityInMinute)))
            .signWith(secretKey)
            .compact();
    }

    private String createRefreshToken(LocalDateTime time, String username) {
        return builder()
            .subject(username)
            .claim(TOKEN_TYPE, REFRESH_TOKEN)
            .issuedAt(Timestamp.valueOf(time))
            .expiration(Timestamp.valueOf(time.plusDays(refreshTokenValidityInDay)))
            .signWith(secretKey)
            .compact();
    }

    private Claims verifyToken(String token) {
        Jws<Claims> claimsJws = jwtParser.parseSignedClaims(token);
        return claimsJws.getPayload();
    }

    public String getUserIdFromToken(String refreshToken) {
        Claims claims = verifyToken(refreshToken);
        return claims.getSubject();
    }
}