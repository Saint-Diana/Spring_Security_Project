package com.shen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shen.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shen
 * @date 2022/12/19 9:04
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userid);
}
