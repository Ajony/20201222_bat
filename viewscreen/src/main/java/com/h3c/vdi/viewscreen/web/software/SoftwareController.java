/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: SoftwareController
 * Author:   ykf8829
 * Date:     2020/7/6 19:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.web.software;

import com.h3c.vdi.viewscreen.api.model.response.college.SoftwareDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.service.software.SoftwareService;
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
 * @create 2020/7/6
 * @since 1.0.0
 */
@RestController
@RequestMapping("/software")
@Slf4j
public class SoftwareController {

    @Resource
    private SoftwareService softwareService;

    @GetMapping()
    public ApiResponse<List<SoftwareDto>> softwareDtoList(@RequestParam(value = "uuid",required = false) String uuid) {
        return softwareService.softwareList(uuid);
    }
}
