package com.log.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {
    private List<T> records;
    private long size;
    private long current;
    private long total;

}
