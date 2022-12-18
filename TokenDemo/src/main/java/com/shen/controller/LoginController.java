package com.shen.controller;

import com.shen.entity.ResponseResult;
import com.shen.entity.User;
import com.shen.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义登录接口，然后让Spring Security对这个接口放行，让用户访问这个接口的时候不用登录也能访问。
 * 在接口中，我们通过AuthenticationManager的authenticate方法来进行用户认证，
 * 所以需要在SecurityConfig中配置AuthenticationManager
 *
 * @author Shen
 * @date 2022/12/18 15:28
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }


    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
