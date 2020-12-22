package com.h3c.vdi.viewscreen.dto;

/**
 * Created by x19765 on 2020/10/14.
 */

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 教室实体DTO
 *
 * @author z13465 on 2019/8/14
 */
@Data
@ToString
public class ClassroomDTO {
    private Long id;
    private Long uid;
    private String name;
    private String position;
    private Integer seat;
    private Boolean teacherVm;
    private Boolean dhcp;
    private String startIp;
    private String endIp;
    private String netMask;
    private String gateway;
    private Integer vlanId;
    private String allowList;
    private Short mode;
    private Short status;
    private Short scheduleOffline;
    private Date scheduleClean;
    private Integer scheduleExamClean;
    private Boolean merge;
    private Long masterClassroomId;
    private Short permissionType;
    private Long teacherId;
    private Long desktopPoolId;
    private Long voiDesktopPoolId;
    private Long networkStrategyId;
    private Long aclStrategyId;
    private Date gmtCreate;
    private Date gmtModified;
    private Boolean mandatoryLogin;
    private Boolean forceLogin;
    private String dns;
    private String secondDns;
    private Long courseImageId;
    private Integer attenders;
    private Boolean allowSelfStudy;
    private String teacherIp;
    private String teacherName;
    private Integer teacherPort;
   // private List<ClassroomPermissionDTO> permissions;
    /**
     * 云桌面属性
     */
//    private Long clusterId;
    /**
     * 授权策略属性
     */
    private Boolean clientShutdown;
    private Boolean clientRestart;
    private Boolean clientShutoff;
    private Boolean redirectUsb;
    private Boolean interactShutdown;
    /**
     * UIS属性
     */
    private Long operatorGroupId;
    private String operatorGroupCode;

    private String masterClassroomName;
    private String courseImageName;

    /**
     * VOI属性
     */
    private Integer offlineDate;
    private String terminalType;


}
