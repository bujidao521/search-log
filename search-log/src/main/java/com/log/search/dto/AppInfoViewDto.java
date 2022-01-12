package com.log.search.dto;

import lombok.Data;

@Data
public class AppInfoViewDto {
    /**
     * id
     */
    private Long id;


    /**
     * 应用名称
     */
    private String appName;

    /**
     * 日志默认路径
     */
    private String logPath;

    private Integer instanceCount;
}
