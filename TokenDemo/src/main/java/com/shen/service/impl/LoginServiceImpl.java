package com.shen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.entity.LoginUser;
import com.shen.entity.ResponseResult;
import com.shen.entity.User;
import com.shen.mapper.UserMapper;
import com.shen.service.LoginService;
import com.shen.utils.JwtUtil;
import com.shen.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Shen
 * @date 2022/12/18 14:46
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //使用AuthenticationManager的authenticate方法进行用户认证
        //将用户名和密码封装为Authentication类型的对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userId生成一个jwt
        //此时authenticate里存放的用户信息是LoginUser类型的，是loadUserByUsername方法返回的封装好的UserDetails的实现类LoginUser类型的对象。
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(id), 1000 * 60 * 60 * 24 * 7L);
        //封装成token，返回给前端
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        //把用户信息存入redis，userId作为key
        redisCache.setCacheObject("login:" + id, loginUser, 7, TimeUnit.DAYS);
        return new ResponseResult<>(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        String redisKey = "login:" + userid;
        //删除redis中的值，那么即使用户携带了之前的token，也会有“用户未登录”异常，因为redis当中的值被删除了！
        redisCache.deleteObject(redisKey);
        return new ResponseResult<Void>(200, "注销成功");
    }
}
