package com.log.search.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.log.search.dto.AppInstanceRequestDto;
import com.log.search.dto.ViewResult;
import com.log.search.entity.AppInstance;
import com.log.search.service.AppInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instance")
public class AppInstanceController {
    @Autowired
    private AppInstanceService appInstanceService;

    @PostMapping("/saveOrUpdate")
    public ViewResult saveOrUpdate(@RequestBody AppInstance appInstance){
        Integer num = appInstanceService.saveOrUpdate(appInstance);
        if(num >0){
            return ViewResult.success("保存成功",num);
        }
        return ViewResult.failed("保存失败");

    }
    @GetMapping("/delete")
    public ViewResult delete(Integer id){

        int num = appInstanceService.delete(id);
        if(num >0){
            return ViewResult.success("删除成功",num);
        }
        return ViewResult.failed("删除失败");



    }
    @PostMapping("/list")
    public ViewResult<IPage<AppInstance>> list(@RequestBody AppInstanceRequestDto<AppInstance> requestDto){

        return ViewResult.success(appInstanceService.list(requestDto));

    }}
