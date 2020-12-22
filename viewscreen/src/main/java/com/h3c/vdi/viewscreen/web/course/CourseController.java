/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CourseController
 * Author:   ykf8829
 * Date:     2020/6/16 14:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.web.course;

import com.h3c.vdi.viewscreen.api.model.response.college.CourseDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.CourseImageDTO;
import com.h3c.vdi.viewscreen.service.course.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/16
 * @since 1.0.0
 */
@RestController
@RequestMapping("/course")
@Api(tags = "course-controller")
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping()
    public ApiResponse<List<CourseDto>> courseDtoList(@RequestParam(value = "uuid",required = false) String uuid) {
            return courseService.courseList(uuid);
    }

    @ApiOperation(value = "查询课程")
    @GetMapping(value = "/queryAllCourse")
    public ApiResponse<List<CourseImageDTO>> queryCourseList(@RequestParam(value = "uuid",required = false) String uuid) {
        return courseService.queryCourseList(uuid);
    }
}