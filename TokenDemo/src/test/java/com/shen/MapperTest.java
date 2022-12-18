package com.shen;

import com.shen.entity.User;
import com.shen.mapper.UserMapper;
import com.shen.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Shen
 * @date 2022/12/18 11:24
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testUserMapper(){
        User user = new User();
        user.setUsername("1034813562");
        user.setNickname("沈慧昌");
        user.setPassword("shc123");
        int i = userMapper.insert(user);
        System.out.println("插入了" + i + "条记录");
    }

    @Test
    public void testBCryptPasswordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("shc123");
        boolean isEqual = passwordEncoder.matches("shc123", password);
        System.out.println(password);
        System.out.println(isEqual);
    }

    @Test
    public void testJwtUtils() throws Exception {
        Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhY2I1NzQ1NjA3ZGY0MWRjYjBkYmRkMTBlMTNkYWU2ZiIsInN1YiI6IjE2MDQzMTc0MjAzNTk1NjUzMTQiLCJpc3MiOiJzaGVuIiwiaWF0IjoxNjcxMzU1MTI5LCJleHAiOjE2NzE5NTk5Mjl9._tMkGr7YhnE2vpXOjgS3Mijum4aLQWJyv1SxipBH8JQ");
        String subject = claims.getSubject();
        System.out.println(subject);
        System.out.println(claims);
    }
}
