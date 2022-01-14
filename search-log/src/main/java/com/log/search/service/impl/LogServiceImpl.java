package com.log.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.log.search.dto.LogInfoDto;
import com.log.search.dto.LogSearchDto;
import com.log.search.entity.AppInstance;
import com.log.search.entity.Shell;
import com.log.search.mapper.AppInstanceMapper;
import com.log.search.service.IShellService;
import com.log.search.service.LogService;
import com.log.search.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
@Slf4j
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private AppInstanceMapper instanceMapper;
    @Autowired
    private IShellService shellService;
    @Override
    public List<LogInfoDto>  searchLog(LogSearchDto searchDto) {
        List<AppInstance> appInstances = instanceMapper.selectList(
                new QueryWrapper<AppInstance>()
                        .eq("app_id", searchDto.getAppId())
        );
        List<LogInfoDto> logInfoList = new ArrayList<>();
        List<Future<LogInfoDto>> futures = new ArrayList<>();
        for (AppInstance appInstance : appInstances) {

            Future<LogInfoDto> future = ThreadPoolUtil.submit(() -> {
                return getLogInfo(searchDto, appInstance);
            });
            futures.add(future);
        }
        for (Future<LogInfoDto> future : futures) {
            try {
                LogInfoDto logInfoDto = future.get();
                logInfoList.add(logInfoDto);
            }catch (Exception e){
                log.error("日志查询执行异常",e);

            }

        }

        return logInfoList;

    }

    private LogInfoDto getLogInfo(LogSearchDto searchDto,  AppInstance appInstance) {
        Shell shell = new Shell();
        shell.setIp(appInstance.getShellHost());
        shell.setUsername(appInstance.getShellUser());
        shell.setPassword(appInstance.getShellPass());

        StringBuilder sb = new StringBuilder("cat ");
        sb.append(appInstance.getLogPath());
        sb.append("info.search-log.log");
        sb.append(" | tail -n -");
        sb.append(searchDto.getTailNum());
        sb.append(" | grep ");
        sb.append(searchDto.getKeyword());
        sb.append(" -");
        sb.append(searchDto.getUpLine());
        log.info("{}开始执行",appInstance.getShellHost());
        List<String> outList = shellService.exec(shell,sb.toString());
        log.info("{}执行结束",appInstance.getShellHost());

        if(!CollectionUtils.isEmpty(outList)){
            String highLight = "<a style='background: #e6ff5b'>" + searchDto.getKeyword() + "</a>";
            outList = outList.stream().map(e-> e.replaceAll(searchDto.getKeyword(), highLight)).collect(Collectors.toList());
        }
        LogInfoDto logInfoDto = new LogInfoDto();
        logInfoDto.setShellHost(appInstance.getShellHost());
        logInfoDto.setKeyword(searchDto.getKeyword());
        logInfoDto.setLogs(outList);
        return logInfoDto;
    }
    private List<String> getFileName(AppInstance appInstance) {
        Shell shell = new Shell();
        shell.setIp(appInstance.getShellHost());
        shell.setUsername(appInstance.getShellUser());
        shell.setPassword(appInstance.getShellPass());


        log.info("{}开始执行",appInstance.getShellHost());
        List<String> outList = shellService.exec(shell,"ls " +appInstance.getLogPath());
        log.info("{}执行结束",appInstance.getShellHost());

        return outList;
    }

    @Override
    public Set<String> searchFileName(long appId) {
        List<AppInstance> appInstances = instanceMapper.selectList(
                new QueryWrapper<AppInstance>()
                        .eq("app_id", appId)
        );
        List<LogInfoDto> logInfoList = new ArrayList<>();
        List<Future<List<String>>> futures = new ArrayList<>();
        for (AppInstance appInstance : appInstances) {

            Future<List<String>> future = ThreadPoolUtil.submit(() -> {
                return getFileName(appInstance);
            });
            futures.add(future);
        }
        Set<String> fileSet = new LinkedHashSet<>();
        for (Future<List<String>> future : futures) {
            try {
                List<String> fileNames = future.get();
                fileSet.addAll(fileNames);
            }catch (Exception e){
                log.error("文件查询执行异常",e);

            }

        }
        return fileSet;
    }

    private LogInfoDto getAllLogInfo(LogSearchDto searchDto,  AppInstance appInstance) {
        Shell shell = new Shell();
        shell.setIp(appInstance.getShellHost());
        shell.setUsername(appInstance.getShellUser());
        shell.setPassword(appInstance.getShellPass());

        StringBuilder sb = new StringBuilder("cat ");
        sb.append(appInstance.getLogPath());
        sb.append("info.search-log.log");
        sb.append(" | tail -n -");
        sb.append(searchDto.getTailNum());

        log.info("{}开始执行",appInstance.getShellHost());
        List<String> outList = shellService.exec(shell,sb.toString());
        log.info("{}执行结束",appInstance.getShellHost());

        LogInfoDto logInfoDto = new LogInfoDto();
        logInfoDto.setShellHost(appInstance.getShellHost());
        logInfoDto.setLogs(outList);
        return logInfoDto;
    }

    @Override
    public List<LogInfoDto> allSearch(LogSearchDto searchDto) {
        List<AppInstance> appInstances = instanceMapper.selectList(
                new QueryWrapper<AppInstance>()
                        .eq("app_id", searchDto.getAppId())
        );
        List<LogInfoDto> logInfoList = new ArrayList<>();
        List<Future<LogInfoDto>> futures = new ArrayList<>();
        for (AppInstance appInstance : appInstances) {

            Future<LogInfoDto> future = ThreadPoolUtil.submit(() -> {
                return getAllLogInfo(searchDto, appInstance);
            });
            futures.add(future);
        }
        for (Future<LogInfoDto> future : futures) {
            try {
                LogInfoDto logInfoDto = future.get();
                logInfoList.add(logInfoDto);
            }catch (Exception e){
                log.error("日志查询执行异常",e);

            }

        }
        return logInfoList;
    }

}
