package com.shen.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shen
 * @date 2022/12/17 20:16
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('test')")   //只有拥有"test"权限的用户才可以访问该请求
    public String hello(){
        return "hello";
    }
}
