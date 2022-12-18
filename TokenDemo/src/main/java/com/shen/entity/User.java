package com.shen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Shen
 * @date 2022/12/18 9:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态(0正常，1停用)
     */
    private String status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户性别（0男、1女、2未知）
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型（0管理员、1普通用户）
     */
    private String userType;

    /**
     * 创建人的用户id
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标志（0表示未删除、1表示已删除）
     */
    private Integer delFlag;
}