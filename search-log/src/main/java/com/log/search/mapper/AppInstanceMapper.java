package com.log.search.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.log.search.entity.AppInstance;
import org.apache.ibatis.annotations.Param;

public interface AppInstanceMapper extends BaseMapper<AppInstance> {
    int updateById(@Param("instance") AppInstance instance);


}