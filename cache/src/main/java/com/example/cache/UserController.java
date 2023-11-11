package com.example.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    @GetMapping("/users/{id}/email")
    public String getUserEmail(@PathVariable Long id) {
        try (var jedis = jedisPool.getResource()) {
            var userEmailRedisKey = "users:%d:email".formatted(id);
            var userEmail = jedis.get("users:%d:email".formatted(id));
            if (userEmail != null) {
                return userEmail;
            }
            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();

            jedis.setex(userEmailRedisKey, 30, userEmail);
            return userEmail;
        }
    }
}
