package com.shen.config;

import com.shen.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 *      自定义PasswordEncoder（BCryptPasswordEncoder）注入容器，替代Spring Security默认的PasswordEncoder
 *
 * @author Shen
 * @date 2022/12/18 15:08
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启授权相关配置，使用注解(@PreAuthorize)去指定访问对应的资源所需的权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口，放行
                .antMatchers("/user/login").anonymous()
                //除上面以外的所有请求都需要认证
                .anyRequest().authenticated();

        //在Spring Security的FilterChain中添加上我们自定义的JWT认证过滤器，这个过滤器需要放在UsernamePasswordAuthenticationFilter前，也就是在最开始先
        //进行token的解析认证
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
