package com.h3c.vdi.viewscreen.constant;

/**
 * @author lgw2845
 * @since 2020/6/4 17:40
 */
public interface Constant {

    String WS_TOKEN_HEADER = "Auth-Token";

    String WS_REFRESH_HEADER = "Ws-Refresh-Token";

    String REFRESH_TOKEN_TYPE = "Local";


    interface Message {

        String IP_CONFIG = "set default ip";
        String ATTEND_CLASS = "上课";
        String FINISH_CLASS = "下课";
        String PAGE = "分页";
        String DEFAULT_PAGE = "默认页";
        String ERROR_REPORT_1 = "上报机构错误！";
        String ERROR_REPORT_2 = "上报数据错误！";
        String ERROR_REPORT_3 = "未上报，请上报该机构后重新上报数据！";

        String ERROR_FAILURE = "登录失败，账号或密码有误！";
        String ERROR_UPD_PWD_FAILURE = "修改密码失败!";
        String ERROR_OLD_PWD_FAILURE = "旧密码有误！";
        String ERROR_USER_NON = "用户不存在！";
        String ERROR_NEW_PWD_NON_OLD_PWD_ALIKE = "新密码不能和旧密码相同！";

        String ERROR_TOKEN_INVALID = "token无效";
        String ERROR_TOKEN_EXPIRES = "token过期";
        String ERROR_TOKEN_DECODE = "token解析错误";

        String ERROR_PWD_REGEX = "必须字母数字特殊字符组合";
        String ERROR_PWD_LENGTH = "密码最小长度为8，最大长度为32";
        String ERROR_PWD_BLANK = "密码不可有空格";
        String ERROR_MES_1 = "该机构处于上报状态，请关闭上报状态后重新删除。";
        String ERROR_MES_2 = "该机构";
        String ERROR_MES_3 = "处于上报状态，请确认已选机构上报状态为关闭后重新删除。";

    }

    interface DateTimeFormat {

        String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

        String DATE_TIME = "yyyy-MM-dd HH:mm";

        String DATE_YEAR_MONTH = "yyyy-MM";

        String DATE_MONTH_DAY = "MM-dd";

        String DATE_PATTERN = "yyyy-MM-dd";

        String TIME_PATTERN = "HH:mm:ss";

        String HOUR_MINUTE = "HH:mm";


    }


    interface ReportType {

        int REPORT_COLLEGE_INFO = 1;            //局点信息
        int REPORT_CLASSROOM = 2;               //教室
        int REPORT_COURSE = 3;                  //课程
        int REPORT_HOST = 4;                    //主机
        int REPORT_VM = 5;                      //虚拟机表
        int REPORT_COLLEGE_DYNAMIC = 6;         //局点动态表(全国)
        int REPORT_COLLEGE_LAST_DYNAMIC = 7;     //最新动态表（省市）
        int REPORT_STORAGE_COLONY_INFO = 8;     //存储集群信息表
        int REPORT_VERSION_INFO = 9;             //版本信息表
        int REPORT_SOFTWARE = 10;                //软件排行表

    }

    interface LogicDelete {
        String LOGIC_DELETE_N = "n";
        String LOGIC_DELETE_Y = "y";
    }

    interface ReportCount {
        Integer REPORT_COUNT = 4;  //每小时上报次数
    }

    interface Cloudos {
        String API_CALULATE_NODELIST = "/os/bingo/v1/calculateNode/findCalculateNodeList";
        String CLOUDOS_TOKEN_HEADER = "X-Auth-Token";
    }

    interface EnvPath {
        String CLOUD_ON = "cloudos.installer.viewscreen";
        String CLOUD_DOWN = "docker.installer.viewscreen";
    }

}
