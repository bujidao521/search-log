package com.log.search.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.log.search.dto.AppInstanceRequestDto;
import com.log.search.entity.AppInfo;
import com.log.search.entity.AppInstance;
import com.log.search.mapper.AppInfoMapper;
import com.log.search.mapper.AppInstanceMapper;
import com.log.search.service.AppInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppInstanceServiceImpl  implements AppInstanceService{

    @Autowired
    private AppInstanceMapper instanceMapper;
    @Autowired
    private AppInfoMapper appInfoMapper;

    @Override
    public Integer saveOrUpdate(AppInstance appInstance) {
        if(StrUtil.isBlank(appInstance.getLogPath())){
            AppInfo appInfo = appInfoMapper.selectById(appInstance.getAppId());
            appInstance.setLogPath(appInfo.getLogPath());
        }
        if(appInstance.getId() == null){
            appInstance.setUpdateTime(new Date());
            appInstance.setCreateTime(new Date());
            return instanceMapper.insert(appInstance);
        }else {
            appInstance.setUpdateTime(new Date());
            return instanceMapper.updateById(appInstance);
        }
    }

    @Override
    public int delete(Integer id) {
        return instanceMapper.deleteById(id);
    }

    @Override
    public IPage<AppInstance> list(AppInstanceRequestDto<AppInstance> requestDto) {
        IPage<AppInstance> page = requestDto.getPage();
        page = instanceMapper.selectPage(page,new QueryWrapper<AppInstance>()
                .eq("app_id",requestDto.getAppId()));
        for (AppInstance record : page.getRecords()) {
            record.setShellPass(null);
        }
        return page;
    }
}
