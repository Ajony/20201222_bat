package com.h3c.vdi.viewscreen.security.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Date 2020/7/21 11:45
 * @Created by lgw2845
 */
@Getter
@Setter
public class ResponseRole implements Serializable {

    private static final long serialVersionUID = 3438579520476068444L;

    private Long id;

    private String name;

    private String description;

    private String logicDelete;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime addDate;

}
