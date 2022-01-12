package com.log.search.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "app_info")
public class AppInfo {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;



    /**
     * 应用名称
     */
    @TableField(value = "app_name")
    private String appName;

    /**
     * 日志默认路径
     */
    @TableField(value = "log_path")
    private String logPath;


    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}