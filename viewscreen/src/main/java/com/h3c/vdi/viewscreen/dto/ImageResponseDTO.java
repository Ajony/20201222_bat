package com.h3c.vdi.viewscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by x19765 on 2020/10/22.
 */

@Data
@ApiModel
@ToString
@EqualsAndHashCode
@XmlRootElement(name = "domain")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageResponseDTO {

    private static final long serialVersionUID = -4471848386315891114L;

    @ApiModelProperty(value = "镜像id")
    private Long id;

    @ApiModelProperty(value = "虚拟机id")
    private Long domainId;

    @ApiModelProperty(value = "虚拟机uuid")
    private String domainUuid;

    @ApiModelProperty(value = "虚拟机名称")
    private String domainName;

    @ApiModelProperty(value = "计算机名")
    private String title;

    @ApiModelProperty(value = "集群 id")
    private Long clusterId;

    @ApiModelProperty(value = "主机id")
    private Long hostId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "内存大小 MB")
    private Integer memory;

    @ApiModelProperty(value = "分配的内存大小")
    private Double memoryInit;

    @ApiModelProperty(value = "cpu")
    private String cpu;

    @ApiModelProperty(value = "vdi模板 id")
    private Long templateId;

    @ApiModelProperty(value = "vdi模板 uuid")
    private String templateUuid;

    @ApiModelProperty(value = "存储大小GB")
    private Long storage;

    @ApiModelProperty(value = "0:Windows;1:Linux ")
    private Integer system;

    @ApiModelProperty(value = "系统")
    private String osVersion;

    @ApiModelProperty(value = "模板存储类型")
    private String templetStoragePath;

    @ApiModelProperty(value = "当前状态，1为虚拟机；2为CAS对应的template; 3为IDV所使用的镜像文件")
    private Integer imageStatus;

    @ApiModelProperty(value = "虚拟机镜像文件类型")
    private String terminalType;

    @ApiModelProperty(hidden = true)
    private Short addFrom;

    @ApiModelProperty(value = "镜像类型，VDI为0，IDV为1", example = "1")
    private Integer templateType;

    @ApiModelProperty(name = "镜像创建时间")
    private Long createTime;

    @ApiModelProperty(name = "虚拟机ip")
    private List<String> ips;

    @ApiModelProperty(name = "父镜像name")
    private String parentImageName;
}
