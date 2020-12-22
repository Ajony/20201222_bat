/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TerminalController
 * Author:   ykf8829
 * Date:     2020/6/28 10:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.web.terminal;

import com.h3c.vdi.viewscreen.api.model.response.college.TerminalDto;
import com.h3c.vdi.viewscreen.api.model.response.college.TerminalUseTimeDto;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.ClassroomDTO;
import com.h3c.vdi.viewscreen.dto.VdiDeviceDTO;
import com.h3c.vdi.viewscreen.service.college.CollegeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/28
 * @since 1.0.0
 */
@RestController
@RequestMapping("/terminal")
@Slf4j
public class TerminalController {

    @Resource
    private CollegeService collegeService;

    @GetMapping()
    public ApiResponse<List<TerminalUseTimeDto>> terminalList(@RequestParam(value = "uuid",required = false) String uuid) {
        return collegeService.terminalUseTimeDtoList(uuid);
    }

    @GetMapping("info")
    public ApiResponse<TerminalDto> terminalInfo(@RequestParam(value = "uuid",required = false) String uuid){
        return collegeService.terminalInfo(uuid);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResponse<List<VdiDeviceDTO>> queryTerminalsByClassroom(@PathVariable(value = "id") Long classroomId,
                                                                     @RequestParam(value = "uuid",required = false) String uuid) {
        return collegeService.queryTerminalsByClassroom(uuid, classroomId);
    }

    @ApiOperation("终端开机")
    @RequestMapping(value = "/wakeUp", method = RequestMethod.PUT)
    public ApiResponse<Boolean> wakeUpTerminal(@RequestParam(value = "uuid",required = false) String uuid,
                                               @ApiParam("设备id列表") @RequestBody List<Integer> ids) {
        return collegeService.wakeUpTerminal(uuid, ids);
    }

    @ApiOperation("终端关机")
    @RequestMapping(value = "/shutDown", method = RequestMethod.PUT)
    public ApiResponse<Boolean> shutDownTerminal(@RequestParam(value = "uuid",required = false) String uuid,
                                                 @ApiParam("设备id列表") @RequestBody List<Integer> ids) {
        return collegeService.shutDownTerminal(uuid, ids);
    }

    @ApiOperation("终端重启")
    @RequestMapping(value = "/restart", method = RequestMethod.PUT)
    public ApiResponse<Boolean> restartTerminal(@RequestParam(value = "uuid",required = false) String uuid,
                                                @ApiParam("设备id列表") @RequestBody List<Integer> ids) {
        return collegeService.restartTerminal(uuid, ids);
    }
}
