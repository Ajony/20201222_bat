package com.h3c.vdi.viewscreen.api.model.response.computenode;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CloudOSCalulateNode {
    /**
     * clusterId : 1
     * clusterName : cluster1
     * hostName : cpn-hostname
     * userName : admin
     * password : admin
     * poolName : CloudClassHostPool
     * poolId : 1
     * hostIp : 10.125.8.73
     * vmType : 2
     * netNames : ["physicnet"]
     * nets : physicnet
     * tableList : null
     * vmName : H3C CAS
     * storageZone : cinder_azone
     * vxlanOverlayMode : 3
     * vxlanModelName : cloudos.matrix.vlan
     * initMode : vmware_api
     * vswitchList : null
     * pcis : []
     * vmwareVersion : null
     * switchType : null
     * igniteServe : null
     * igniteLoginUser : null
     * igniteLoginPasswd : null
     * volgroup : null
     * kernelAddressPool : null
     * kernelAddressMask : null
     * kernelAddressGateway : null
     * info : null
     * cloudIP : null
     */

    private String clusterId;
    private String clusterName;
    private String hostName;
    private String userName;
    private String password;
    private String poolName;
    private String poolId;
    private String hostIp;
    private int vmType;
    private String nets;
    private Object tableList;
    private String vmName;
    private String storageZone;
    private String vxlanOverlayMode;
    private String vxlanModelName;
    private String initMode;
    private Object vswitchList;
    private Object vmwareVersion;
    private Object switchType;
    private Object igniteServe;
    private Object igniteLoginUser;
    private Object igniteLoginPasswd;
    private Object volgroup;
    private Object kernelAddressPool;
    private Object kernelAddressMask;
    private Object kernelAddressGateway;
    private Object info;
    private Object cloudIP;
    private List<String> netNames;
    private List<?> pcis;
}
