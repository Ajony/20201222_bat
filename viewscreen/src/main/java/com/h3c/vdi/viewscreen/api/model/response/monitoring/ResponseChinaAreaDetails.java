package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author lgq
 * @since 2020/6/22 11:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "地区详细信息")
public class ResponseChinaAreaDetails {

    private Long id;

    private Long addressParentId;

    private String addressName;

    private Integer addressType;

    private String addressCode;

    private String extName;

    private Long classSum;

    private Long desktopSum;

    private Long schoolSum;

    private Long courseSum;

    private Long hostSum;

    private Long terminalSum;

    private Long userSum;

    private String logicDelete;

//    private String location;

    private Map<String,Object> location;

//    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
//    private LocalDateTime addDate;

//    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
//    private LocalDateTime modifiedDate;

//    private String version;
}
