package com.h3c.vdi.viewscreen.web.bureaudot;

import com.h3c.vdi.viewscreen.api.model.response.bureaudot.ResponsePageBureauDot;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.api.result.page.ApiPage;
import com.h3c.vdi.viewscreen.service.bureaudot.BureauDotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Date 2020/11/3 11:37
 * @Created by lgw2845
 */

@RequestMapping("/bureauDot")
@RestController
@Log4j2
@Api(tags = "bureau-dot-controller")
@Validated
public class BureauDotController {

    @Resource
    BureauDotService bureauDotService;


    @ApiOperation(value = "获取分页数据(删除)", notes = "当前页和每页条数必填数字,分页查询（删除）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "current", required = true, defaultValue = "1", value = "起始页码", example = "1"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "size", required = true, defaultValue = "5", value = "每页条数", example = "5"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "provinceName", value = "请求参数,省市"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "cityName", value = "请求参数，市区"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "countyName", value = "请求参数，区县"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "college", value = "请求参数，机构")
    })
    @GetMapping(value = "/page")
    public ApiResponse<ApiPage<ResponsePageBureauDot>> page(@RequestParam("current") @NotNull(message = "not null current") Integer current,
                                                            @RequestParam("size") @NotNull(message = "not null size") Integer size,
                                                            @RequestParam(value = "provinceName", required = false) String provinceName,
                                                            @RequestParam(value = "cityName", required = false) String cityName,
                                                            @RequestParam(value = "countyName", required = false) String countyName,
                                                            @RequestParam(value = "college", required = false) String college) {
        return bureauDotService.page(current, size, provinceName, cityName, countyName, college);
    }


//    @ApiOperation(value = "获取分页数据(首页机构动态)", notes = "当前页和每页条数必填数字,分页查询(首页机构动态)")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "int", name = "current", required = true, defaultValue = "1", value = "起始页码", example = "1"),
//            @ApiImplicitParam(paramType = "query", dataType = "int", name = "size", required = true, defaultValue = "5", value = "每页条数", example = "5"),
//    })
//    @GetMapping(value = "/homoPage")
//    public ApiResponse<ApiPage<ResponsePageBureauDot>> homoPage(@RequestParam("current") @NotNull(message = "not null current") Integer current,
//                                                                @RequestParam("size") @NotNull(message = "not null size") Integer size) {
//        return bureauDotService.homoPage(current, size);
//    }


    /**
     * 删除局点信息的数据
     *
     * @param id
     * @return
     */
//    @ApiOperation("删除局点信息的数据")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "string", name = "id", value = "id", required = true)
//    })
//    @DeleteMapping(value = "/delete")
//    ApiResponse<Boolean> delete(@RequestParam("id") @NotBlank(message = "id不能为空") String id) {
//        return bureauDotService.delete(id);
//    }


    /**
     * 删除局点信息的数据，根据由逗号（,）分隔的多个ID组成的字符串
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除局点信息的数据，根据由逗号（,）分隔的多个ID组成的字符串")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "ids", value = "由逗号（,）分隔的多个ID组成的字符串", required = true)
    })
    @DeleteMapping(value = "/deleteByIds")
    public ApiResponse<Boolean> deleteByIds(@RequestParam("ids") @NotBlank(message = "ids不能为空") String ids) {
        return bureauDotService.deleteByIds(ids);
    }


}
