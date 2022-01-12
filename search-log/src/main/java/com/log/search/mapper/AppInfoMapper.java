package com.log.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.log.search.entity.AppInfo;
import org.apache.ibatis.annotations.Param;

public interface AppInfoMapper extends BaseMapper<AppInfo> {
    Page<AppInfo> listOption(Page<AppInfo> page, @Param("keyword")String keyword);


}