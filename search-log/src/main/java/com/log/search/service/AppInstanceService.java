package com.log.search.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.log.search.dto.AppInstanceRequestDto;
import com.log.search.entity.AppInstance;
public interface AppInstanceService {

    public Integer saveOrUpdate(AppInstance appInstance);

    int delete(Integer id);

    IPage<AppInstance> list(AppInstanceRequestDto<AppInstance> requestDto);
}
