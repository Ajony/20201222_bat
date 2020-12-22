package com.h3c.vdi.viewscreen.dto;

/**
 * Created by x19765 on 2020/10/22.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Restful Web Services 接口请求/返回的虚拟机实体类。
 */
@Data
@ToString
@XmlRootElement(name = "domain")
@XmlAccessorType(XmlAccessType.FIELD)
public class RsDomain implements Serializable {

    private static final long serialVersionUID = 7630696172783821368L;

    /**
     * 虚拟机ID。
     */
    private Long id = null;

    /**
     * 物理机ID。 *
     */
    private Long hostId;

    /**
     * 集群ID。
     */
    private Long clusterId = null;

    /**
     * 主机池ID。
     */
    private Long hostPoolId;

    /**
     * 外部云资源ID
     */
    private Long publicCloudId;

    /**
     * 通过模板部署的虚拟机模板ID，为空则不是通过部署或者升级上来的虚拟机。
     */
    private Long templateId;

    /**
     * 虚拟机名称。（uis接口）
     */
    private String domainName;

    /**
     * 虚拟机名称。
     */
    private String name;

    /**
     * 虚拟机显示名称。 *
     */
    private String title;

    /**
     * 虚拟机所在集群是否启用HA
     */
    private Integer enableHA;

    /**
     * 对应UIS数据库ENABLE值
     */
    private Integer enable;

    /**
     * cpu数量最大值
     */
    private Integer maxCpuSocket;
    /**
     * 虚拟机镜像文件名
     */
    private String imgFileName;
    /**
     * 虚拟机镜像文件类型
     */
    private String imgFileType;

    /**
     * 内存值
     */
    private Double memoryInit;
    /**
     * 内存单位
     */
    private String memoryUnit;

    /**
     * 模式
     */
    private String cpuMode;

    /**
     * 虚拟交换机ID
     */
    private Long vSwitchId;
    /**
     * 虚拟交换机名称
     */
    private String vSwitchName;

    /**
     * 端口策略模板ID
     */
    private Long profileId;
    /**
     * 端口策略模板名称
     */
    private String profileName;

    /**
     * 主机名称。 *
     */
    private String hostName;

    /**
     * 虚拟机描述。 *
     */
    private String description;

    /**
     * 虚拟机内存。兆。 *
     */
    private Long memory;

    private String memoryStr;

    private Integer memoryLocked;

    /**
     * 内存预留百分比, 0--100即(0%--100%),0表示不预留，100表示全部预留。其他为部分预留.
     */
    private Integer memoryBacking;

    /**
     * 内存资源优先级。 *
     */
    private Integer memoryPriority;

    /**
     * 虚拟机CPU利用率 *
     */
    private Double cpuRate;

    /**
     * 虚拟机内存利用率 *
     */
    private Double memRate;

    /**
     * 虚拟机虚拟CPU个数（CPU个数 * CPU核数）。 *
     */
    private Integer cpu;

    /**
     * 虚拟机CPU个数
     */
    private Integer cpuSockets;

    /**
     * 虚拟机CPU个数（UIS接口）
     */
    private Integer cpuSocket;


    /**
     * 虚拟机CPU核数
     */
    private Integer cpuCore;

    /**
     * 增加虚拟机自动加载软驱
     */
    private Boolean autoLoadVirtio;

    /**
     * 虚拟机CPU核数
     */
    private Integer cpuCores;

    /**
     * 虚拟机CPU最大核数
     */
    private Integer cpuMax;

    /**
     * 虚拟机占用物理CPU优先级。 高=2048，中=1024，低=512
     */
    private Integer cpuShares;

    /**
     * 虚拟机I/O权重。
     */
    private Integer blkiotune;

    /**
     * 虚拟机状态。取值： 0:模板 1:未知 2:运行 3:关闭 4 暂停。 *
     */
    private Integer status;

    /**
     * 虚拟机状态 *
     */
    private String vmStatus;

    /**
     * 虚拟机UUID。 *
     */
    private String uuid;

    /**
     * 虚拟机BIT。取值： x86_64 x86。 *
     */
    private String osBit;

    /**
     * 虚拟机安装的操作系统。取值：0:Windows;1:Linux。 *
     */
    private Integer system;
    /**
     * 虚拟机安装的操作系统描述。 *
     */
    private String osDesc;
    /**
     * 虚拟机安装的操作系统版本。 *
     */
    private String osVersion;

    /**
     * 操作系统安装方式: cd image none
     */
    private String osInstallMode;

    /**
     * 安装操作系统镜像文件路径
     */
    private String imagePath;

    /**
     * 显示类型。 *
     */
    private String viewType;

    /**
     * 显示驱动 vga;cirrus;vmvga。 *
     */
    private String drive;

    /**
     * 显示端口。 *
     */
    private Integer viewPort;

    /**
     * 显示监听地址。 *
     */
    private String monitorAddr;

    /**
     * 引导设备：1 disk 2 cdrom。 *
     */
    private Integer bootingDevice;

    /**
     * 是否自动启动：0:不自动启动1:自动启动。 *
     */
    private Integer autoBooting;

    /**
     * 虚拟机网络。
     */
    @XmlElement(name = "network")
    private List<RsDomainNetwork> networks = null;

    /**
     * 虚拟机存储。
     */
    @XmlElement(name = "storage")
    @JsonProperty("storage")
    private List<RsDomainStorage> storages = null;

    /**
     * 虚拟机启动优先级 0:低级 1:中级 2:高级。
     */
    private Integer priority = null;

    /**
     * 虚拟机是否允许自动迁移。
     */
    private Integer autoMigrate = null;

    /**
     * 1为开启， 可以为空或0表示不开启。castools自动升级
     */
    private Integer autoTools = 1;

    /**
     * 虚拟机是否启用VNC代理。1：启用，0或空为不启用。
     */
    private Integer enableVncProxy = null;

    /**
     * 虚拟机创建日期。
     */
    private Date createDate;

    /**
     * 存储总容量。 *
     */
    private Long storageCapacity;

    /**
     * 登录IP地址。 *
     */
    private String ipAddr;

    /**
     * 虚拟机所属标志 0:不属于用户以及用户组 1:属于用户 2:属于用户组。
     */
    private Integer flag = 1;

    /**
     * 虚拟机类型 0:CAS虚拟机 1:VMware虚拟机。
     */
    private Integer type = 0;

    /**
     * 是否快速部署过。
     */
    private Boolean deployed = false;

    /**
     * VNC 端口 自动配置0：自动1：手动。 *
     */
    private Integer auto;

    /**
     * 是否可以转换格式 *
     */
    private Integer formatEnable;

    /**
     * 是否存在raw格式*
     */
    private Boolean existRaw;


    /**
     * 虚拟机过期日期*
     */
    private Date expireDate;

    /**
     * 虚拟CPU与物理CPU的绑定关系。(UIS)
     **/
    private Map<Integer, String> vcpupin;

    /**
     * 计算机名称(UIS)
     */
    private String computerName;

    private String platformName;

    /**
     * 虚拟机过期日期字符串*
     */

    private String expireDateStr;

    /**
     * CPU周期限制值
     */
    private Double cpuQuota;

    /**
     * CPU周期限制单位
     */
    private String cpuQuotaUnit;

    private Long cpuGurantee;

    /**
     * 虚拟桌面的分配模式 1：固定桌面池 2：浮动桌面池
     */
    private Integer assignMode;

    /**
     * spice访问uri*
     */
    private String spiceUri;

    /**
     * 原主机ID，记录迁移前的主机ID，用于迁移过程中HA异常处理（UIS）
     */
    private Long srcHostId;

    /**
     * 虚拟机是否启用保护模式。 1：启用，0：不启用 *
     */
    private Integer protectModel;

    /**
     * 是否存在块设备*
     */
    private boolean existBlock;

    /**
     * 是否存在PCI或USB设备*
     */
    private boolean existPciOrUsb;

    /**
     * 是否存在光驱或软驱*
     */
    private boolean existCdromOrFloppy;

    /**
     * 写入domainxml文件中cpu quota值。
     */
    private Long cpuTuneQuota;

    /**
     * 表示该虚拟机的状态是否更改
     */
    private boolean isChanged;

    /**
     * 增量备份和差异备份时，与上次备份相比，虚拟机磁盘列表是否发生改变 1：是，0：否 (UIS)
     */
    private Long diskChange = 0L;

    /**
     * castool状态 0:未运行 1:运行
     */
    private Integer castoolsStatus;

    /**
     * castool版本
     */
    private String castoolsVersion;

    private Set<RsDomainIpConfig> domainIpConfig;

    private List<RsDomainDev> devList;

    /**
     * 模板存放目录
     */
    private String templetStoragePath;

    /**
     * 虚拟机初始化状态 0：虚拟机需要再次启动，1：虚拟机需要将2级存储改为3级 ，2：虚拟机已经完成完全初始化
     */
    private Integer startStatus;

    /**
     * 虚拟机HA异常状态
     */
    private Integer haStatus;

    /**
     * 虚拟机HA管理状态
     */
    private Integer haManage;

    /**
     * 声卡类型：ac97，ich6
     */

    private String soundType;

//    List<RsPci> pci;
//
//    List<RsUsb> usb;
//
//    List<RsGpu> gpu;

    private Integer secretLevel;

    private Long operatorGroupId;

    private String operatorGroupCode;

    /**
     * 内存限制
     */
    private Double memoryLimit;

    /**
     * 内存限制单元
     */
    private String memoryLimitUnit;

    /**
     * 虚拟机逻辑回收时间(UIS)
     */
    private long delTime;

    /**
     * 虚拟机创建还原点时间(UIS)
     */
    private Date createRestorePointDate;

    private long lastUpdateTime = System.currentTimeMillis() / 1000;

    /**
     * 最近还原时间（uis）
     */
    private Date lastRestoreDate;

    private int uptime = 0;

    /**
     * 写入domainxml文件中 是否开启内存气球功能。 0-关闭， 1-开启
     **/
    private Integer autoMem = 0;

    /**
     * 虚拟机是否启用防病毒配置。1：启用，0为不启用。
     */
    private int antivirusEnable = 0;

    /**
     * 是否开启完整性校验：0或空:不启用, 1：启用
     **/
    private Integer integrityCheck;

    private List<RsDomainDev> bindPcpu;

    private long lastOffTime = 0;

    private boolean hugepage = false;

    private Boolean domainCpuGlobalQuota = false;

    private Boolean enableReduceCPU = false;

    private String watchdogAction;

    private boolean holdDevName;

    /**
     * 虚拟机图标
     */
    private String icon;

    /**
     * 重要虚拟机标识
     */
    private Integer important;

    /**
     * 创建虚拟机时是否启用定时备份
     */
    private Integer isBackup;

    /**
     * 备份名称
     */
    private String backupName;

    /**
     * 是否启用cpu热添加
     */
    private Integer cpuHot;

    /**
     * 是否启用内存热添加
     */
    private Integer memoryHot;

    /**
     * 是否启用磁盘热添加
     */
    private Integer diskHot;

    /**
     * 是否启用网卡热添加
     */
    private Integer netHot;

    /**
     * 主机故障后，是否重启
     */
    private Integer osha;

    /**
     * 引导设备详情
     */
//    private RsBootDetail bootDetail;

    /**
     * 鼠标类型
     */
    private String mouseType;

    /**
     * 虚拟机快速配置
     */
    private String quickConfig;

    /**
     * 虚拟网络增加虚拟机时, 拖入图形的坐标位置
     */
    private Location topoLocation;

    /**
     * Vdi创建虚拟机需要spice控制台
     **/
    private Boolean spice = true;

//    @XmlElement(name = "ipv4Attribute")
//    private List<RsNetInfo>ipv4Attributes;

    /*虚拟机分类 默认为1 1 普通虚拟机  101~200由VDI自定义（101，VDI镜像 ； 102 --110 预留 ； 111开始为IDV不同终端类型对应镜像）*/
    private Integer vmType = 1;

    private Long maxMemory = 512*1024*1024L;

    /**
     * 获取 cpu 总核数
     */
    public Integer getCpu() {
        return getCpuSockets() != null && getCpuCores() != null ? Integer.valueOf(getCpuSockets() * getCpuCores()) : cpu;
    }


    public String getVmStatus() {
        if (vmStatus != null && !vmStatus.isEmpty()) {
            return vmStatus;
        }
        if (status != null) {
            if (2 == status) {
                return "running";
            }
            if (3 == status) {
                return "shutOff";
            }
            if (4 == status) {
                return "paused";
            }
            return "unknown";
        }
        return vmStatus;
    }

    public Integer getStatus() {
        if (null == status && StringUtils.isNotBlank(vmStatus)){
            if (vmStatus.equals("unknown")){
                return 1;
            }
            if (vmStatus.equals("running")){
                return 2;
            }
            if (vmStatus.equals("shutOff")){
                return 3;
            }
            if (vmStatus.equals("paused")){
                return 4;
            }
            return 5;
        }
        return status;
    }

    @Data
    public static class Location implements Serializable {
        private static final long serialVersionUID = 19882056072L;
        /**
         * 坐标的x,y位置
         */
        private Float x;
        private Float y;
    }
//
//    public static DomainDTO transferToDomainDTO(RsDomain rsDomain) {
//        DomainDTO dto = new DomainDTO();
//        Utils.copyProperties(dto, rsDomain);
//        dto.setStorages(new ArrayList<>());
//        if (Objects.nonNull(rsDomain.getStorages())) {
//            for (RsDomainStorage rsDomainStorage : rsDomain.getStorages()) {
//                DomainStorageDTO domainStorageDTO = new DomainStorageDTO();
//                Utils.copyProperties(domainStorageDTO, rsDomainStorage);
//                dto.getStorages().add(domainStorageDTO);
//            }
//        }
//        //现在cas接口查询出来虚拟机操作系统版本存放在osDesc字段里
//        if (Objects.isNull(rsDomain.getOsVersion())) {
//            dto.setOsVersion(rsDomain.getOsDesc());
//        }
//        return dto;
//    }
}

