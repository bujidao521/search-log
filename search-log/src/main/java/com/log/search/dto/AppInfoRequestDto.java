package com.log.search.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class AppInfoRequestDto<T> {
    private Page<T> page;
    private String keyword;

}
