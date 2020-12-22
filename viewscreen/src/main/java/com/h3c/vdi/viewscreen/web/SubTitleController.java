package com.h3c.vdi.viewscreen.web;


import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.SubTitleDTO;
import com.h3c.vdi.viewscreen.service.SubTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by x19765 on 2020/10/14.
 */
@RestController
@RequestMapping("/subtitle")
@Api(tags = "subtitle-controller")
@Slf4j
public class SubTitleController {

    @Resource
    private SubTitleService subTitleService;

    @ApiOperation(value = "编辑小标题接口", notes = "编辑小标题")
    @PostMapping(value = "/edit")
    public ApiResponse<Boolean> editSubTitle(@RequestBody SubTitleDTO subTitleDTO) {
        return subTitleService.editSubTitle(subTitleDTO);
    }

    @ApiOperation(value = "查询小标题接口", notes = "查询小标题")
    @GetMapping(value = "")
    public ApiResponse<SubTitleDTO> getSubTitle() {
        return subTitleService.getSubTitle();
    }


}
