package com.h3c.vdi.viewscreen.security.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h3c.vdi.viewscreen.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Date 2020/7/21 11:41
 * @Created by lgw2845
 */
@Data
@ApiModel(description = "用户信息")
public class ResponseUser implements Serializable {

    private static final long serialVersionUID = -1291833932636486311L;
    private Long id;

    private String username;

    private String password;

    private String description;

    private String logicDelete;

    @JsonFormat(pattern = Constant.DateTimeFormat.DATE_TIME_PATTERN)
    private LocalDateTime addDate;

    private List<ResponseRole> roles;

}
