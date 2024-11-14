package ds.project.toy.global.util;

import ds.project.toy.global.common.vo.RedisPrefix;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValueWithExpire(RedisPrefix prefix, String key, String value,
        Duration duration) {
        redisTemplate.opsForValue().set(createKey(prefix, key), value, duration);
    }

    public String getValue(RedisPrefix prefix, String key) {
        return (String) redisTemplate.opsForValue().get(createKey(prefix, key));
    }

    public void deleteValue(RedisPrefix prefix, String key) {
        redisTemplate.delete(createKey(prefix, key));
    }

    public boolean hasKey(RedisPrefix prefix, String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(createKey(prefix, key)));
    }

    private String createKey(RedisPrefix prefix, String key) {
        return prefix + ":" + key;
    }
}
