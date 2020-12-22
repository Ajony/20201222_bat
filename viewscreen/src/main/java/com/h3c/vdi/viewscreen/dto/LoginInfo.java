package com.h3c.vdi.viewscreen.dto;

/**
 * Created by x19765 on 2020/10/23.
 */

import java.io.Serializable;
import java.util.*;

/**
 * 操作员登录信息。
 *
 * @author NAME
 */
public class LoginInfo implements Serializable {

    /** 序列化ID。 */
    private static final long serialVersionUID = 1L;

    /** 登录名。 */
    private String loginName = null;

    /** 登录密码。 */
    private String pwd = null;

    /** 操作员ID。 */
    private Long id = null;

    /** 操作员姓名。 */
    private String userName = null;

    /** 证件号码。 */
    private String credentialNumber = null;

    /** 电子邮件地址。 */
    private String email = null;

    /** 闲置超时时长（秒）。 */
    private long idleTimeout = 0L;

    /** 当前是否在线。 */
    private boolean online = false;

    /** 登录时间。 */
    private Date loginTime;

    /** 登录地址。 */
    private String loginIp = null;

    /** HTTP Session ID。 */
    private String sessionId = null;

    /** 登录失败的错误码 */
    private Integer loginFailErrorCode = 0;

    /** 登录失败的消息（只要该信息不为 <code>null</code>，就表示登录操作失败）。 */
    private String loginFailMessage = null;

    /** 其他附加信息。 */
    private Map<String, Object> additionalInfo = new HashMap<String, Object>();

    /** 权限信息（包含操作员权限及操作员所属的所有群组权限）。 */
    private Set<String> permissions = new HashSet<String>();

    /** 操作员分组ID。 */
    private Long operatorGroupId = null;

    /** 操作员分组名称。 */
    private String operatorGroupName = null;

    /** 操作员分组编码。 */
    private String operatorGroupCode = null;

    /**操作员分组类型**/
    private Integer operatorGroupMode = null;

    /** 是否可以管理子分组，1: 可管理子分组, 0：不可管理子分组。 */
    private Integer flag = null;

    /** 容灾服务  */
    private boolean applySRM = false;

    /** 版本 */
    private Integer version = 1;

    private String forward = null;

    /**皮肤**/
    private String operSkin = null;

    private String initComplete = null;

    private String selectLanguage = null;

    private boolean isSsoLogin = false;

    private String uuid;

    private Integer pwdExpireDay=Integer.MAX_VALUE;

    //授权类型 0-本地管理员  1-ldap管理员
    private Integer authType;

    public String getOperSkin() {
        return operSkin;
    }

    public void setOperSkin(String operSkin) {
        this.operSkin = operSkin;
    }

    /**
     * 使用传入参数，更新当前实体的值。
     *
     * @param loginInfo 待更新的信息。
     */
    public void update(LoginInfo loginInfo) {
        loginName = loginInfo.loginName;
        pwd = loginInfo.pwd;
        id = loginInfo.id;
        userName = loginInfo.userName;
        credentialNumber = loginInfo.credentialNumber;
        email = loginInfo.email;
        idleTimeout = loginInfo.idleTimeout;
        online = loginInfo.online;
        loginTime = loginInfo.loginTime;
        loginIp = loginInfo.loginIp;
        operatorGroupId = loginInfo.operatorGroupId;
        operatorGroupCode = loginInfo.operatorGroupCode;
        operatorGroupName = loginInfo.operatorGroupName;
        operatorGroupMode = loginInfo.operatorGroupMode;
        applySRM = loginInfo.applySRM;
        version = loginInfo.version;
        flag = loginInfo.flag;
        additionalInfo.clear();
        additionalInfo.putAll(loginInfo.additionalInfo);
        permissions.clear();
        permissions.addAll(loginInfo.permissions);
        forward = loginInfo.forward;
    }

    /**
     * 返回特定功能权限是否允许使用。
     *
     * @param permissionId 功能权限ID。
     * @return 是否允许。
     */
    public boolean isFunctionAllowed(String permissionId) {
        return permissions.contains(permissionId);
    }

    // ------------------------------------------------------------------- 访问方法

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCredentialNumber() {
        return credentialNumber;
    }

    public void setCredentialNumber(String credentialNumber) {
        this.credentialNumber = credentialNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Date getLoginTime() {
        return loginTime != null ? new Date(loginTime.getTime()) : null;
    }

    public void setLoginTime(Date loginTime) {
        if (loginTime != null) {
            if (this.loginTime != null) {
                this.loginTime.setTime(loginTime.getTime());
            } else {
                this.loginTime = new Date(loginTime.getTime());
            }
        } else{
            this.loginTime = null;
        }
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getLoginFailErrorCode() {
        return loginFailErrorCode;
    }

    public void setLoginFailErrorCode(Integer loginFailErrorCode) {
        this.loginFailErrorCode = loginFailErrorCode;
    }

    public String getLoginFailMessage() {
        return loginFailMessage;
    }

    public void setLoginFailMessage(String loginFailMessage) {
        this.loginFailMessage = loginFailMessage;
    }

    public Long getOperatorGroupId() {
        return operatorGroupId;
    }

    public void setOperatorGroupId(Long operatorGroupId) {
        this.operatorGroupId = operatorGroupId;
    }

    public String getOperatorGroupCode() {
        return operatorGroupCode;
    }

    public void setOperatorGroupCode(String operatorGroupCode) {
        this.operatorGroupCode = operatorGroupCode;
    }

    public String getOperatorGroupName() {
        return operatorGroupName;
    }

    public void setOperatorGroupName(String operatorGroupName) {
        this.operatorGroupName = operatorGroupName;
    }

    public Integer getOperatorGroupMode() {
        return operatorGroupMode;
    }

    public void setOperatorGroupMode(Integer operatorGroupMode) {
        this.operatorGroupMode = operatorGroupMode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isSsoLogin() {
        return isSsoLogin;
    }

    public void setSsoLogin(boolean isSsoLogin) {
        this.isSsoLogin = isSsoLogin;
    }

    /**
     * 增加附加信息。
     *
     * @param key 附加信息键值。
     * @param value 附加信息值。
     */
    public void addAdditionalInfo(String key, Object value) {
        additionalInfo.put(key, value);
    }

    /**
     * 删除附加信息。
     *
     * @param key 附加信息键值。
     * @return 被删除的附加信息值。
     */
    public Object removeAdditionalInfo(String key) {
        return additionalInfo.remove(key);
    }

    /**
     * 删除所有附加信息。
     */
    public void removeAllAdditionalInfo() {
        additionalInfo.clear();
    }


    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public Object getAdditional(String key) {
        return additionalInfo.get(key);
    }
    /**
     * 设置权限信息（包含操作员权限及操作员所属的所有群组权限）。
     *
     * @param permissions 权限信息（包含操作员权限及操作员所属的所有群组权限）。
     */
    public void setPermissions(Set<String> permissions) {
        this.permissions.clear();
        this.permissions.addAll(permissions);
    }
    public Set<String> getPermissions() {
        return this.permissions;
    }
    public boolean isApplySRM() {
        return applySRM;
    }

    public void setApplySRM(boolean isApplySRM) {
        this.applySRM = isApplySRM;
    }

    // ----------------------------------------------------------------- 覆盖基类方法



    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "LoginInfo {loginName=" + loginName + ", loginIp=" + loginIp + ", loginTime="
                + loginTime + "}";
    }

    public String getInitComplete() {
        return initComplete;
    }

    public void setInitComplete(String initComplete) {
        this.initComplete = initComplete;
    }

    public String getSelectLanguage() {
        return selectLanguage;
    }

    public void setSelectLanguage(String selectLanguage) {
        this.selectLanguage = selectLanguage;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPwdExpireDay() { return pwdExpireDay;}

    public void setPwdExpireDay(Integer pwdExpireDay){
        this.pwdExpireDay= pwdExpireDay;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }
}
