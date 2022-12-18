package com.shen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shen.entity.ResponseResult;
import com.shen.entity.User;

/**
 * @author Shen
 * @date 2022/12/18 14:46
 */
public interface LoginService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logout();
}
