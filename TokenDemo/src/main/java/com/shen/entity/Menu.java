package com.shen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Shen
 * @date 2022/12/19 8:52
 */
@Data
@TableName("sys_menu")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Menu {
    @TableId("id")
    private Long id;

    @TableField("menu_name")
    private String menuName;

    @TableField("path")
    private String path;

    @TableField("component")
    private String component;

    @TableField("visible")
    private String visible;

    @TableField("status")
    private String status;

    @TableField("perms")
    private String perms;

    @TableField("icon")
    private String icon;

    @TableField("created_by")
    private String createdBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("updated_by")
    private String updatedBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("remark")
    private String remark;
}
