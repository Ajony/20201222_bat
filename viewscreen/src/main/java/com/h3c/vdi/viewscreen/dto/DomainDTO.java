package com.h3c.vdi.viewscreen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by x19765 on 2020/10/14.
 */

@Data
@ApiModel(value = "虚拟机详情")
public class DomainDTO implements Serializable {
    private static final long serialVersionUID = -8444581379564409479L;
    /** 1:未知 2:运行 3:关闭 4 暂停。 */
    public static final int Unknown = 1;
    public static final int RUNNING = 2;
    public static final int ShutOff = 3;
    public static final int PAUSED = 4;
    public static final int ABNORMAL = 5;

    /** 虚拟机id。 **/
    @ApiModelProperty(name = "虚拟机id", example = "7")
    private Long id;

    /** 物理机ID。 **/
    @ApiModelProperty(name = "虚拟机所在主机ID", example = "7")
    private Long hostId;

    /** 虚拟机所在主机名称。 */
    @ApiModelProperty(name = "虚拟机所在主机名称", example = "host1")
    private String hostName;

    /** 集群ID。*/
    @ApiModelProperty(name = "集群ID", example = "7")
    private Long clusterId;

    /** 主机池ID。*/
    @ApiModelProperty(name = "主机池ID", example = "7")
    private Long hostPoolId;

    /** 虚拟机标题名称。 **/
    @ApiModelProperty(name = "虚拟机标题名称", example = "title1")
    private String title;

    /** 计算机名 **/
    @ApiModelProperty(name = "计算机名", example = "com1")
    private String computerName;

    /** 虚拟机CPU个数。 **/
    @ApiModelProperty(name = "虚拟机CPU个数", example = "2")
    private Integer cpu;

    /**虚拟机状态：当虚拟机的HA_STATUS不为0但有别的值时为"ha_exception",
     * 当虚拟机的STATUS为3时值为"shutoff",当虚拟机的STATUS为4时值为"paused",其他为"unknow"*/
    /** 1:未知 2:运行 3:关闭 4 暂停。5:异常 */
    //todo 用int还是String？domain中是int，domaindisplay中是string
    @ApiModelProperty(name = "虚拟机状态1:未知 2:运行 3:关闭 4 暂停。5:异常", example = "4")
    private Integer status;
    /** 虚拟机安装的操作系统。取值：0:Windows;1:Linux,2:BSD **/
    @ApiModelProperty(name = "虚拟机安装的操作系统。取值：0:Windows;1:Linux,2:BSD", example = "0")
    private Integer system;

    /** 虚拟机状态。取值：1:虚拟机 2:模板 3:删除的虚拟机 4：创建中**/
    @ApiModelProperty(name = "虚拟机状态。取值：1:虚拟机 2:模板 3:删除的虚拟机 4：创建中", example = "1")
    private Integer enable;

    /** 虚拟机是否启用保护模式。 1：启用，0：不启用 **/
    @ApiModelProperty(name = " 虚拟机是否启用保护模式。 1：启用，0：不启用", example = "1")
    private Integer protectModel;

    /**castool状态 0WEIYUNXING 1 YUNXING */
    @ApiModelProperty(name = "castool状态 0WEIYUNXING 1 YUNXING", example = "0")
    private Integer castoolsStatus;

    /**castool版本*/
    @ApiModelProperty(name = "castool版本")
    private String castoolsVersion;

    @ApiModelProperty(name = "agent版本")
    private String agentVersion;

    @ApiModelProperty(name = "ip地址")
    private String ipv4;

    /** 虚拟机CPU利用率。 */
    @ApiModelProperty(name = "虚拟机CPU利用率")
    private Double cpuRate;

    /** 虚拟机内存利用率。 */
    @ApiModelProperty(name = "虚拟机内存利用率")
    private Double memRate;

    /**castools状态：noRunnig未运行、running运行、--其他     */
    @ApiModelProperty(name = "castools状态：noRunnig未运行、running运行、--其他", example = "running")
    private String castools;

    /**
     * 桌面池名称
     */
    @ApiModelProperty(name = "桌面池名称", example = "pool")
    private String desktopPoolName;

    /**
     * 桌面池id
     */
    @ApiModelProperty(name = "桌面池id", example = "7")
    private Long desktopPoolId;

    /**
     * 桌面池类型
     */
    @ApiModelProperty(name = "桌面池类型", example = "vdi")
    private Integer desktopPoolType;

    @ApiModelProperty(name = "桌面池用户类型")
    private Integer desktopPoolUserType;

    /**
     * 授权用户或者设备名称
     */
    @ApiModelProperty(name = "授权用户或者设备名称", example = "user1")
    private String loginName;

    /**
     * 授权用户的用户姓名
     */
    @ApiModelProperty(name = "授权用户的用户姓名", example = "cmy")
    private String userName;

    /**
     * 授权用户或者设备ID
     */
    @ApiModelProperty(name = "授权用户或者设备ID", example = "7")
    private Long authObjectId;

    /**
     * 授权类型
     */
    @ApiModelProperty(name = "虚机授权关联的数据类型,1：用户 0：分组 2：设备 3：设备分组", example = "0")
    private Integer authType;
//
//    /**
//     * 桌面池的用户类型：本地用户、域用户、设备用户
//     */
//    private Integer desktopPoolUserType;

    /**
     * 授权策略ID
     */
    @ApiModelProperty(name = "授权策略ID", example = "7")
    private Long authStgId;

    /**
     * 授权策略名称
     */
    @ApiModelProperty(name = "授权策略名称")
    private String authStgName;

    /**
     * 设备IP
     */
    @ApiModelProperty(name = "设备IP")
    private String authDeviceIp;

    /**
     * 设备MAC
     */
    @ApiModelProperty(name = "设备MAC")
    private String authDeviceMac;

    /**
     * 虚拟机登录时间
     */
    @ApiModelProperty(name = "虚拟机登录时间")
    private Long loginTime;

    /**
     * 虚拟机闲置时长
     */
    @ApiModelProperty(name = "虚拟机闲置时间")
    private String idleTime;

    /**
     * 锁定状态：0表示锁定，1表示解锁
     */
    @ApiModelProperty(name = "锁定状态：0表示锁定，1表示解锁", example = "0")
    private Integer lockStatus = 1;

    /**
     * 存储总容量。 *
     */
    @ApiModelProperty(name = " 存储总容量")
    private Long storageCapacity;

    /**
     * 登录IP地址。 *  todo,有ipv4了,怎么还有这个?
     */
    @ApiModelProperty(name = "登录IP地址")
    private String ipAddr;

    /**
     * 虚拟机状态 *
     */
    @ApiModelProperty(name = "虚拟机状态")
    private String vmStatus;

    /**
     * 虚拟机安装的操作系统版本。 *
     */
    @ApiModelProperty(name = "虚拟机安装的操作系统版本")
    private String osVersion;

    /**
     * 集群名称
     */
    @ApiModelProperty(name = "集群名称")
    private String clusterName;

    @ApiModelProperty(name = "虚拟机实体名称")
    private String domainName;

    /**
     * 虚拟机存储。
     */
    @ApiModelProperty(name = "虚拟机存储")
    private List<DomainStorageDTO> storages = null;

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
            if (5 == status) {
                return "abnormal";
            }
            return "unknown";
        }
        return vmStatus;
    }

    public Integer getProtectModel(){
        if (protectModel != null){
            return protectModel;
        }else {
            return 0;
        }
    }

    /**
     * 云学堂所需
     */
    private String ip;     //todo,有ipv4了,怎么还有这个?
    private String mac;
    private String terminalIp;
    private String terminalMac;
    private String hostIp;
    private Long classroomId;
    private String classroomName;
    private Integer order;

    private Integer computerType;

    /**
     * 云桌面创建时间
     */
    private Date createTime;

    private String uuid;

    private String templateName;

    private String templateUuid;

    private String tenantId;

    private String userUuid;

    @ApiModelProperty(value = "桌面池授权类型：1：静态或单用户，2：动态或多用户，3：手工或匿名用户,4:匿名登录（其中单用户，多用户，匿名用户为IDV类型）", example = "1")
    private Integer assignMode;

    public void setStatus(Integer status) {
        if (status.equals(DomainDTO.ABNORMAL)){
            if (null != this.status){
                if (this.status.equals(DomainDTO.RUNNING)){
                    //只针对运行状态的虚拟机判断agent状态是否正常
                    this.status = status;
                }
            }else {
                this.status = status;
            }
        }else {
            this.status = status;
        }
    }

    //idv 前端用id传值
    public String getUuid() {
        return StringUtils.isEmpty(uuid) ?
                id!=null ? id.toString() : ""
                : uuid;
    }

    private String displayName;
}
