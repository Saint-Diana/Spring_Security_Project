package com.shen.service.impl;

import com.shen.entity.LoginUser;
import com.shen.entity.User;
import com.shen.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 自定义UserDetailsService实现类
 *      重载loadUserByUsername方法：
 *          根据用户名，从数据库中查询用户信息及用户对应的权限信息
 *          把对应的用户信息包括权限信息封装成UserDetails对象返回
 *
 * @author Shen
 * @date 2022/12/18 14:42
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        User user = userMapper.selectByUsername(username);
        if(Objects.isNull(user)){
            //如果没有查询到用户，就抛出异常
            throw new RuntimeException("用户名错误");
        }
        //TODO 查询对应的权限信息
        //设置权限信息数组
        List<String> list = new ArrayList<>(Arrays.asList("test", "admin", "user", "guest"));
        //把用户封装为UserDetails接口实现类返回
        return new LoginUser(user, list);
    }
}
