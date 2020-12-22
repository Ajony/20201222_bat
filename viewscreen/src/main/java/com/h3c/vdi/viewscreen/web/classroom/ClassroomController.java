/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ClassroomController
 * Author:   ykf8829
 * Date:     2020/6/24 9:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.web.classroom;

import com.h3c.vdi.viewscreen.api.model.response.college.ClassroomClassDto;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.ClassroomDTO;
import com.h3c.vdi.viewscreen.dto.DesktopStatisticsDTO;
import com.h3c.vdi.viewscreen.dto.DomainDTO;
import com.h3c.vdi.viewscreen.service.classroom.ClassroomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classroomClass")
@Api(tags = "classroomClass-controller")
@Slf4j
public class ClassroomController {

    @Resource
    private ClassroomService classroomService;

    @GetMapping()
    public ApiResponse<List<ClassroomClassDto>> ClassroomClass(@RequestParam(value = "uuid",required = false) String uuid){
        return classroomService.classroomList(uuid);
    }


    @ApiOperation(value = "检测IP")
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ApiResponse<Boolean> ping(@RequestParam(value = "ip") String ip) {
        return classroomService.ping(ip);
    }


    @ApiOperation(value = "查询教室")
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public ApiResponse<List<ClassroomDTO>> getClassroomInfo(@RequestParam(value = "uuid",required = false) String uuid) {
        return classroomService.getClassroomInfo(uuid);
    }

    @ApiOperation(value = "查询教室")
    @RequestMapping(value = "/queryClassroomById", method = RequestMethod.GET)
    public ApiResponse<ClassroomDTO> getClassroomInfoById(@RequestParam(value = "uuid",required = false) String uuid,
                                                          @RequestParam(value = "id",required = false) Long id) {
        return classroomService.getClassroomInfoById(uuid, id);
    }

    @ApiOperation(value = "查询教室中的虚拟机")
    @RequestMapping(value = "/vms/{id}", method = RequestMethod.GET)
    public ApiResponse<List<DomainDTO>> queryDesktops(@PathVariable(value = "id") Long classroomId,
                                                      @RequestParam(value = "uuid",required = false) String uuid) {
        return classroomService.queryDomainsByClassroomId(uuid, classroomId);
    }

    @RequestMapping(value = "/desktopStatistics/{id}", method = RequestMethod.GET)
    public ApiResponse<DesktopStatisticsDTO> queryDesktopStatusStatistics(@PathVariable(value = "id") Long id,
                                                                          @RequestParam(value = "uuid",required = false) String uuid) {
        return classroomService.queryDesktopStatusStatistics(uuid, id);
    }

    @RequestMapping(value = "/vms/{id}/shutdown", method = RequestMethod.PUT)
    public ApiResponse<Boolean> shutDownDesktop(@PathVariable(value = "id") Long domainId,
                                                @RequestParam(value = "uuid",required = false) String uuid) {
        return classroomService.shutDownDomain(uuid, domainId);
    }

    @RequestMapping(value = "/vms/{id}/start", method = RequestMethod.PUT)
    public ApiResponse<Boolean> startDesktop(@PathVariable(value = "id") Long domainId,
                                             @RequestParam(value = "uuid",required = false) String uuid) {
        return classroomService.startDomain(uuid,domainId);
    }

    @RequestMapping(value = "/vms/{id}/restart", method = RequestMethod.PUT)
    public ApiResponse<Boolean> restartDesktop(@PathVariable(value = "id") Long domainId,
                                               @RequestParam(value = "uuid",required = false) String uuid) {
        return classroomService.restartDomain(uuid, domainId);
    }
}
