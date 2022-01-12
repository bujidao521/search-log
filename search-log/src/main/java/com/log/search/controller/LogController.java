package com.log.search.controller;

import com.log.search.dto.LogInfoDto;
import com.log.search.dto.LogSearchDto;
import com.log.search.dto.ViewResult;
import com.log.search.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping("/search")
    public ViewResult<List<LogInfoDto>>  search(@RequestBody LogSearchDto searchDto){
        ViewResult<List<LogInfoDto>> viewResult = ViewResult.success(logService.searchLog(searchDto));
        return viewResult;

    }
    @PostMapping("/allSearch")
    public ViewResult<List<LogInfoDto>>  allSearch(@RequestBody LogSearchDto searchDto){
        ViewResult<List<LogInfoDto>> viewResult = ViewResult.success(logService.allSearch(searchDto));
        return viewResult;

    }
    @GetMapping("/searchFileName")
    public ViewResult<Set<String>>  searchFile(@RequestParam long appId){
        return ViewResult.success(logService.searchFileName(appId));

    }
}
