package com.h3c.vdi.viewscreen.constant;

/**
 * Created by x19765 on 2020/10/14.
 */
public interface WsUriConstants {

    Integer WsPort = 8083;

    interface Login {
        String LOGIN = "/vdi/login/doLogin?encrypt=%s&name=%s&password=%s";
    }

    /**
     *
     */
    interface Classroom {
        String BASE = "/vdi/rest/learning-space";
        String QUERY_CLASSROOM_BY_ID = BASE + "/queryClassroomById?classroomId=%s";
        //查询所有教室
        String QUERY_CLASSROOM_LIST = BASE + "/queryAllClassrooms";

    }

    interface Course {
        String BASE = "/vdi/rest/learning-space/data/course";
        String QUERY_COURSE_LIST = BASE + "/queryAllCourse";
    }

    interface ClassOperation {
        String BASE = "/vdi/rest/learning-space";
        String BEGIN_CLASS = BASE + "/class-operation/begin";
        String END_CLASS = BASE + "/class-operation/end";
    }

    interface Terminal {
        String BASE = "/vdi/rest/learning-space";
        String QUERY_TERMINAL_LIST = BASE + "/deviceByClassRoomId?classroomId=%s";
        String TERMINAL_WAKE_UP = BASE + "/wakeUp";
        String TERMINAL_SHUT_DOWN = BASE + "/shutDown";
        String TERMINAL_RESTART = BASE + "/restart";

    }

    interface Domain {
        String BASE = "/vdi/rest/learning-space";
        String QUERY_DOMAIN_LIST = BASE + "/%s/vms";
        String DOMAIN_SHUT_DOWN = BASE + "/%s/vms/shutdown";
        String DOMAIN_START = BASE + "/%s/vms/start";
        String DOMAIN_RESTART = BASE + "/%s/vms/restart";
    }

    interface Storage {
        String BASE = "/vdi/rest/learning-space";
        String STORAGE_IOPS = BASE + "/iops";
        String STORAGE_IO = BASE + "/iorwtrend";

    }

}
