package com.log.search.service;

import com.log.search.dto.AppInfoRequestDto;
import com.log.search.dto.AppInfoViewDto;
import com.log.search.dto.PageVo;
import com.log.search.entity.AppInfo;

import java.util.List;

public interface AppInfoService {
    Integer saveOrUpdate(AppInfo appInfo);

    int delete(Integer id);

    PageVo<AppInfoViewDto> list(AppInfoRequestDto<AppInfo> page);

    List<AppInfo> listOption(String keyword);
}
