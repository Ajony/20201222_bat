package com.h3c.vdi.viewscreen.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by x19765 on 2020/10/22.
 */

@Data
@ToString
public class RsDomainIpConfig implements Serializable {

    private static final long serialVersionUID = 2740465486275250243L;

    private Long id;

    private String mac;

    private Long domainId;

    private String vswitchName;

    private String type;

    private String ipAddr;

    private String mask;

    private Integer enable;

    private String lastUpdateTime;


}
