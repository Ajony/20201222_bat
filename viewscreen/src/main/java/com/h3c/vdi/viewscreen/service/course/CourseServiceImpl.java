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
package com.h3c.vdi.viewscreen.service.course;

import com.h3c.vdi.viewscreen.api.model.response.college.CourseDto;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.WorkspaceRestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.constant.WsUriConstants;
import com.h3c.vdi.viewscreen.dao.CourseDao;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dto.CourseImageDTO;
import com.h3c.vdi.viewscreen.entity.Course;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDao courseDao;

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private WorkspaceRestClient workspaceRestClient;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public ApiResponse<List<CourseDto>> courseList(String uuid) {
        ApiResponse<List<CourseDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<Course> courseList = courseDao.findByUuid(uuid);
        if (!courseList.isEmpty()){
            List<CourseDto> courseDtoList = new LinkedList<>();
            for (Course course : courseList){
                CourseDto courseDto = new CourseDto(course.getCourseName(), course.getClassCount());
                courseDtoList.add(courseDto);
            }
            returnValue = ApiResponse.buildSuccess(courseDtoList);
        }
        return returnValue;
    }

    @Override
    public ApiResponse<List<CourseImageDTO>> queryCourseList(String uuid) {
        ShareArea shareAreas = shareAreaDao.findByUuidAndLogicDelete(uuid,Constant.LogicDelete.LOGIC_DELETE_N);
        if(Objects.isNull(shareAreas)) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        String ip = shareAreas.getIp();
        boolean status = jwtUtil.authVerify(ip);
        if(!status) {
            return ApiResponse.buildError(ApiErrorEnum.E50103, "");
        }
        ApiResponse<List<CourseImageDTO>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Course.QUERY_COURSE_LIST;
        ResponseEntity<List<CourseImageDTO>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CourseImageDTO>>(){});
        List<CourseImageDTO> courseImageDTOList = result.getBody();
        if(CollectionUtils.isNotEmpty(courseImageDTOList)) {
            returnValue = ApiResponse.buildSuccess(courseImageDTOList);
        }
        return returnValue;
    }
}