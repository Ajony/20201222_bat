package com.h3c.vdi.viewscreen.api.model.request.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lgq
 * @since 2020/6/3 11:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class RequestReport<T> {


    @ApiModelProperty(value = "上报类型", example = "0")
    @NotNull(message = "上报类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "JSON数据")
//    @NotEmpty(message = "JSON数据不能为空")
    private List<T> dataList;



}
