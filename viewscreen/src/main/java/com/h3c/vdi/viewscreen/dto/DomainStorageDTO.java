package com.h3c.vdi.viewscreen.dto;

/**
 * Created by x19765 on 2020/10/14.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 虚拟机存储实体DTO
 */
@Data
public class DomainStorageDTO implements Serializable {

    private static final long serialVersionUID = -2123957555512182862L;

    /**
     * 记录ID。
     */
    private Long id = null;

    /**
     * 存储文件名。 *
     */
    private String storeFile;

    /**
     * 容量。 *
     */
    private Long capacity;

    /**
     * 容量最大值
     */
    private Long maxCapacity;

    /**
     * 容量最小值
     */
    private Long minCapacity;

    /**
     * 容量单位 *TB GB MB
     */
    private String capacityUnit;


    /**
     * 有快照不允许修改磁盘容量  0: 允许修改 1：不允许修改
     */
    private Integer enableEditCapacity;

    /**
     * 有多级镜像文件时不允许修改磁盘容量 0：允许修改 1：不允许修改
     */
    private Integer enableConverFmt;

    /**
     * 已用空间。 *
     */
    private Long useSpace;
    /**
     * 容量(空间)利用率。 *
     */
    private Long useSpaceRate;

    /**
     * 存储格式。 *  raw: 高速 qcow2:智能
     */
    private String diskFormat;


    /**
     * 存储设备类型，枚举值：ide：IDE硬盘， scsi：SCSI硬盘， usb：USB硬盘;，virtio：Virtio 硬盘。
     */
    private String targetBus;

    /**
     * 　类型。如file block *
     */
    private String type;


    /**
     * 　存储类型，枚举值：disk：硬盘， cdrom：光驱， floppy：软驱。
     */
    private String device;

    /**
     * 　取值为：Read-Write  Read-ForceWrite  Read-Only。 *
     */
    private String format;

    /**
     * 　存储卷类型。如raw *
     */
    private String driveType;

    /**
     * 分配类型:0指定 1动态分配。 *
     */
    private Integer assignType;

    /**
     * 　取值为：disk cdrom floppy　。 *
     */
    private String diskDevice;

    /**
     * 基础镜像存储文件路径。
     */
    private String backingStore;

    /**
     * 磁盘缓存方式
     */
    private String cacheType;

    /**
     * 镜像存储文件所在的存储池名称。
     */
    private String poolName;

    /**
     * 镜像存储文件所在的存储池类型
     */
    private String poolType;

    /**
     * 源路径
     */
    private String fromPath;
    /**
     * 限制IO速率读
     */
    private String limitIoWrite;
    private Long readBytesSec;
    /**
     * 限制IO速率写
     */
    private String limitIoRead;
    private Long writeBytesSec;
    /**
     * 限制IOPS速率读
     */
    private String limitIopsWrite;
    private Long readIopsSec;
    /**
     * 限制IOPS速率写
     */
    private String limitIopsRead;
    private Long writeIopsSec;

    @Override
    public String toString() {
        return String.format("VmStorage {id=%d, mac=%s}", id, storeFile);
    }
}
