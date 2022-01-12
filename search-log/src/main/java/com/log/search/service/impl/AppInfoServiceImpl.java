package com.log.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.log.search.dto.AppInfoRequestDto;
import com.log.search.dto.AppInfoViewDto;
import com.log.search.dto.PageVo;
import com.log.search.entity.AppInfo;
import com.log.search.entity.AppInstance;
import com.log.search.mapper.AppInfoMapper;
import com.log.search.mapper.AppInstanceMapper;
import com.log.search.service.AppInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppInfoServiceImpl  implements AppInfoService{

    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private AppInstanceMapper instanceMapper;

    @Override
    public Integer saveOrUpdate(AppInfo appInfo) {
        if(appInfo.getId() == null){
            appInfo.setUpdateTime(new Date());
            appInfo.setCreateTime(new Date());
            return appInfoMapper.insert(appInfo);
        }else {
            appInfo.setUpdateTime(new Date());
            return appInfoMapper.updateById(appInfo);
        }
    }

    @Override
    public int delete(Integer id) {
        List<AppInstance> instances = instanceMapper.selectList(
                new QueryWrapper<AppInstance>()
                        .eq("app_id", id)
        );
        List<Long> ids = instances.stream().map(AppInstance::getId).collect(Collectors.toList());
        if(ids != null && ids.size() >0){
            instanceMapper.deleteBatchIds(ids);
        }
       return appInfoMapper.deleteById(id);
    }

    @Override
    public PageVo<AppInfoViewDto> list(AppInfoRequestDto<AppInfo> appInfoRequestDto) {
        IPage<AppInfo> page = appInfoRequestDto.getPage();
        page = appInfoMapper.selectPage(page,new QueryWrapper<AppInfo>());
        PageVo<AppInfoViewDto> pageVo = new PageVo<>();
        pageVo.setCurrent(page.getCurrent());
        pageVo.setSize(page.getSize());
        pageVo.setTotal(page.getTotal());
        List<AppInfo> records = page.getRecords();
        List<AppInfoViewDto> viewDtos = new ArrayList<>();
        pageVo.setRecords(viewDtos);
        for (AppInfo record : records) {
            Integer count = instanceMapper.selectCount(
                    new QueryWrapper<AppInstance>()
                            .eq("app_id", record.getId())
            );
            AppInfoViewDto viewDto = new AppInfoViewDto();
            BeanUtils.copyProperties(record,viewDto);
            viewDto.setInstanceCount(count);
            viewDtos.add(viewDto);
        }
        return pageVo;
    }

    @Override
    public List<AppInfo> listOption(String keyword) {
        Page<AppInfo> page = new Page<>(1, 20);
        Page<AppInfo> infoPage = appInfoMapper.listOption(page, keyword);
        return infoPage.getRecords();
    }
}
