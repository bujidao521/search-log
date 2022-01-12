package com.log.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class LogInfoDto {
    private String shellHost;
    private String keyword;
    private List<String> logs;
}
