package com.h3c.vdi.viewscreen.api.model.response.monitoring;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author lgq
 * @since 2020/7/2 16:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "")
public class ResponseOnLineInfo {

    private Long onLineDesktopSum;

    private Long onLineTerminalSum;

    private Long attendClassSum;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_MONTH_DAY)
    private LocalDate statisticsDate;

}
