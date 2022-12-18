package com.shen;

import com.shen.entity.User;
import com.shen.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
