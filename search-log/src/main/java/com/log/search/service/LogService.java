package com.log.search.service;

import com.log.search.dto.LogInfoDto;
import com.log.search.dto.LogSearchDto;

import java.util.List;
import java.util.Set;

public interface LogService {
    List<LogInfoDto>  searchLog(LogSearchDto searchDto) ;

    Set<String> searchFileName(long appId);

    List<LogInfoDto> allSearch(LogSearchDto searchDto);
}
