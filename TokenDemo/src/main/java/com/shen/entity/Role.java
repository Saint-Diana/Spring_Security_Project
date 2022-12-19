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
 * @date 2022/12/19 8:59
 */
@TableName("sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("role_key")
    private String roleKey;

    @TableField("status")
    private String status;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("created_by")
    private String createdBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("updated_by")
    private String updatedBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("remark")
    private String remark;
}
