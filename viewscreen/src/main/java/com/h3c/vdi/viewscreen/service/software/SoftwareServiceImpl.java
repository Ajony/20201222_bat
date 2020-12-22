/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CourseServiceImpl
 * Author:   ykf8829
 * Date:     2020/5/28 18:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.software;

import com.h3c.vdi.viewscreen.api.model.response.college.SoftwareDto;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dao.SoftwareDao;
import com.h3c.vdi.viewscreen.entity.Software;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/28
 * @since 1.0.0
 */
@Service
@Slf4j
public class SoftwareServiceImpl implements SoftwareService {

    @Resource
    private SoftwareDao softwareDao;

    @Override
    public ApiResponse<List<SoftwareDto>> softwareList(String uuid) {
        ApiResponse<List<SoftwareDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Software> softwareList = softwareDao.findByUuid(uuid);
        if (!softwareList.isEmpty()){
            List<SoftwareDto> softwareDtoList = new LinkedList<>();
            for (Software software : softwareList){
                SoftwareDto softwareDto = new SoftwareDto(software.getSoftwareName(), software.getSoftwareIndex());
                softwareDtoList.add(softwareDto);
            }
            returnValue = ApiResponse.buildSuccess(softwareDtoList);
        }
        return returnValue;
    }
}