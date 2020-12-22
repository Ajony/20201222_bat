/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeController
 * Author:   ykf8829
 * Date:     2020/6/23 19:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.web.college;

import com.h3c.vdi.viewscreen.api.model.response.college.CollegeInfoDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.service.college.CollegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/college")
@Slf4j
public class CollegeController {

    @Resource
    private CollegeService collegeService;

    @GetMapping("collegeInfo")
    public ApiResponse<CollegeInfoDto> CollegeInfo(@RequestParam(value = "uuid", required = false) String uuid) {
        return collegeService.collegeInfo(uuid);
    }

    @GetMapping("changeCollege")
    public ApiResponse<String> changeCollege(@RequestParam(value = "college", required = false) String college) {
        return collegeService.getUuid(college);
    }
}
