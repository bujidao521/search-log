package com.log.search.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "app_instance")
public class AppInstance {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 应用id
     */
    @TableField(value = "app_id")
    private Long appId;

    /**
     * 应用主机
     */
    @TableField(value = "shell_host")
    private String shellHost;

    /**
     * shell端口
     */
    @TableField(value = "shell_port")
    private Integer shellPort;

    /**
     * shell用户
     */
    @TableField(value = "shell_user")
    private String shellUser;

    /**
     * shell密码
     */
    @TableField(value = "shell_pass")
    private String shellPass;

    @TableField(value = "create_user")
    private String createUser;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_user")
    private String updateUser;

    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 日志路径，空则使用app_info的路径
     */
    @TableField(value = "log_path")
    private String logPath;
}