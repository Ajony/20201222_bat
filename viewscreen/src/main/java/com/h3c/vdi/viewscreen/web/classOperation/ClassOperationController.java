package com.h3c.vdi.viewscreen.web.classOperation;

import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.BeginClassReqDTO;
import com.h3c.vdi.viewscreen.dto.EndClassReqDTO;
import com.h3c.vdi.viewscreen.service.classOperation.ClassOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by x19765 on 2020/10/14.
 */
@RestController
@RequestMapping("/class-operation")
@Api(tags = "class-operation-controller")
@Slf4j
public class ClassOperationController {

    @Resource
    private ClassOperationService classOperationService;


    @ApiOperation(value = "上课")
    @PostMapping("/begin")
    public ApiResponse<Boolean> beginClass(@RequestParam(value = "uuid",required = false) String uuid,
                                           @RequestBody BeginClassReqDTO beginClassReqDTO) {
        return classOperationService.beginClassAction(uuid, beginClassReqDTO);
    }

    @ApiOperation(value = "下课")
    @PostMapping("/end")
    public ApiResponse<Boolean> endClass(@RequestParam(value = "uuid",required = false) String uuid,
                                         @RequestBody EndClassReqDTO endClassReqDTO) {
        return classOperationService.endClassAction(uuid, endClassReqDTO);
    }

}
