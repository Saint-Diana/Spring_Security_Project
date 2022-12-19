package com.shen.filter;

import com.shen.entity.LoginUser;
import com.shen.utils.JwtUtil;
import com.shen.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义JWT认证过滤器：
 *      1、获取token
 *      2、解析token获取其中的userid
 *      3、从redis中获取用户信息
 *      4、存入SecurityContextHolder
 *
 * @author Shen
 * @date 2022/12/18 16:56
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //请求放行，因为没有token，说明用户还没有登录，那么放行让请求去登录
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        //从redis中获取token
        String tokenKey = "token:" + userid;
        String redisToken = redisCache.getCacheObject(tokenKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //如果请求头携带的token与redis中存放的token不同，说明这个token已经失效了！
        if(!redisToken.equals(token)){
            throw new RuntimeException("token已失效");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息，封装为Authentication对象
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities()));
        //请求放行
        filterChain.doFilter(request, response);
    }
}
