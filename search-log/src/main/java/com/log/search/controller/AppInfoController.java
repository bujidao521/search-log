package com.log.search.controller;

import com.log.search.dto.AppInfoRequestDto;
import com.log.search.dto.AppInfoViewDto;
import com.log.search.dto.PageVo;
import com.log.search.dto.ViewResult;
import com.log.search.entity.AppInfo;
import com.log.search.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info")
public class AppInfoController {
    @Autowired
    private AppInfoService appInfoService;

    @PostMapping("/saveOrUpdate")
    public ViewResult saveOrUpdate(@RequestBody AppInfo appInfo){

        int num = appInfoService.saveOrUpdate(appInfo);
        if(num >0){
            return ViewResult.success("保存成功",num);
        }
        return ViewResult.failed("保存失败");

    }
    @GetMapping ("/delete")
    public ViewResult delete(@RequestParam Integer id){

        int num = appInfoService.delete(id);
        if(num >0){
            return ViewResult.success("删除成功",num);
        }
        return ViewResult.failed("删除失败");

    }
    @PostMapping("/list")
    public ViewResult<PageVo<AppInfoViewDto>> list(@RequestBody AppInfoRequestDto<AppInfo> page){
        return ViewResult.success(appInfoService.list(page));
    }
    @GetMapping("/listOption")
    public ViewResult<List<AppInfo>> listOption(String keyword){
        return ViewResult.success(appInfoService.listOption(keyword));
    }
}
