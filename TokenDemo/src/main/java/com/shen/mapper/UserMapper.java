package com.shen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shen.entity.User;
import org.apache.ibatis.annotations.Mapper;



/**
 * @author Shen
 * @date 2022/12/18 11:11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public User selectByUsername(String username);
}
