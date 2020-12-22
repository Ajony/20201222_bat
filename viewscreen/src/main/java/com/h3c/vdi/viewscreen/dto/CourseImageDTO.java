package com.h3c.vdi.viewscreen.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by x19765 on 2020/10/20.
 */

@Data
@ToString
public class CourseImageDTO implements Serializable {
    private static final long serialVersionUID = 4052991403298210930L;

    private Long id;

    private String domainUUID;

    private String name;

    private Integer status;

    private RsDomain domain;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer isVGPU;

    private String prefix;
    /**
     * 用户补全规则 (虚拟机桌面后缀)
     * update,add,import
     */
    private String completionRule;

    private Integer type;

    private String imagePath;

    private String description;

    private String lnImagePath;//软连接的图片路径

    //vGPU的帧缓存
    private String vGpuFramebuffer;

    //vGPU的最大分辨率
    private String vGpuMaxResolution;

    //vGPU的最大虚拟个数（显示器接口数）
    private Integer vGpuMaxInstance;

    //课程的备份状态，0:空闲中，1：正在备份中，2：正在清理中
    private Integer backUpStatus;

    //权限控制类型。0：全部开放，1：部分开放，2：全部不开放
    private Short permissionType;

    //上课人数，考试课程人数
    private Long attenders;

    private ImageResponseDTO imageInfo;

    private Integer isDefaultVOICourse;

    private Integer domainStatus;

    //课程已发布的主机名称
    private List<String> publishedHostNames;

}
