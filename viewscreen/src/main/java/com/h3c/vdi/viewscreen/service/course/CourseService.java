/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CourseService
 * Author:   ykf8829
 * Date:     2020/5/28 18:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.course;

import com.h3c.vdi.viewscreen.api.model.response.college.CourseDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.CourseImageDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/28
 * @since 1.0.0
 */
public interface CourseService {
    ApiResponse<List<CourseDto>> courseList(String uuid);

    ApiResponse<List<CourseImageDTO>> queryCourseList(String uuid);
}