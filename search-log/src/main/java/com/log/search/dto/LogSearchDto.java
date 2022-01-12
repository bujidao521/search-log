package com.log.search.dto;

import lombok.Data;

@Data
public class LogSearchDto {
    private Long appId;

    private String keyword;

    private Long tailNum;

    private String upLine;
    private String fileName;
}
