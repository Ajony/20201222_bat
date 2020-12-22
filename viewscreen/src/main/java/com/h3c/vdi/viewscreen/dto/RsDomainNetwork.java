package com.h3c.vdi.viewscreen.dto;

/**
 * Created by x19765 on 2020/10/22.
 */

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Restful Web Services 接口返回的虚拟机网络信息实体类。
 *
 * @author z01500
 */
@Data
@XmlRootElement(name = "network")
@XmlAccessorType(XmlAccessType.FIELD)
public class RsDomainNetwork implements Serializable {

    private static final long serialVersionUID = 2093525599973936332L;

    /**
     * 记录ID。
     */
    private Long id = null;

    /**
     * 是否开启CAS Tools 设置系统网络信息 0:不开启 1：开启
     */
    private int isCasToolsOpen = 0;

    /**
     * 网卡类型：0桥接  1NAT 2不使用网络。 *
     */
    private Integer netType;

    /**
     * 网卡MAC地址。 *
     */
    private String mac;

    /**
     * 网卡IP地址。 *
     */
    private String ipAddr;

    /**
     * 子网掩码。 *
     */
    private String maskAddr;
    private String gateway;
    private String dns;
    private String secondDns;
    /**
     * 虚拟交换机Id。 *
     */
    private Long vsId;

    /**
     * 虚拟交换机名称。 *
     */
    private String vsName;

    /**
     * 　驱动模型，缺省为rtl8139。 *
     */
    private String deviceModel;

    /**
     * 内核加速： 网卡驱动类型为 virtoIo时有效 1:加速，0：否
     */
    private Integer isKernelAccelerated = 1;

    /**
     * 　vlan id。 *
     */
    private Integer vlan;

    private Boolean isLimitInBound;

    /**
     * 　入方向平均带宽。 *
     */
    private Long inAvgBandwidth;

    /**
     * 　入方向峰值带宽。 *
     */
    private Long inPeakBandwidth;

    /**
     * 　入方向突发缓冲大小。 *
     */
    private Long inBurst;

    private Boolean isLimitOutBound;

    /**
     * 　出方向平均带宽。 *
     */
    private Long outAvgBandwidth;

    /**
     * 　出方向峰值带宽。 *
     */
    private Long outPeakBandwidth;

    /**
     * 　出方向突发缓冲大小。 *
     */
    private Long outBurst;

    /**
     * 　转发模式。 *
     */
    private String mode;

    /** 　虚拟交换机转发模式。0 : veb; 1: vepa; 2:多通道 ; 3:分布式**/
    private Integer vsMode;


    private String vsiNetResId;

    /**
     * 网络策略模板ID
     */
    private Long profileId;

    /**
     * 网络策略模板名称。
     */
    private String profileName;

    /**
     * 是否配置了vport ： 配置了vport可以更换为多通道虚拟交换机
     */
    private Boolean virtualport;

    private Boolean bindIp;

    private Boolean bindIpv6;

    /**
     * mtu
     */
    private Long mtu;

    //SR-IOV直通网卡名称。
    private String ethName;

    //驱动类型，枚举值：VFIO：VFIO设备分配处理，KVM：KVM内核处理
    private String driverType;

    //虚拟网卡地址。(SR-IOV网卡)
    private String address;


    public String converMode() {
        /** 　虚拟交换机转发模式。0 : veb; 1: vepa; 2:多通道 ; 3:分布式**/
        //VEPA和多通道已不支持（被淘汰）
        if (this.vsMode == 3) {
            return "dvs";
        }
        return "veb";
    }

}
