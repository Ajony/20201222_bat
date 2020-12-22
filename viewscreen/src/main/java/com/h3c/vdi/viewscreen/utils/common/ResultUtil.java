package com.h3c.vdi.viewscreen.utils.common;

import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果工具类
 */
@Slf4j
public class ResultUtil {

    /**
     * 私有化构造器
     */
    private ResultUtil() {
    }

    /**
     * 使用response输出JSON
     * @Return void
     */
    public static void responseJson(HttpServletResponse response, ApiResponse<?> apiResponse) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            out = response.getWriter();
            out.println(new Gson().newBuilder().serializeNulls().create().toJson(apiResponse));
        } catch (Exception e) {
            log.error("【JSON输出异常】" + e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 返回成功示例
     *
     * @Param resultMap  返回数据MAP
     * @Return Map<String, Object> 返回数据MAP
     */
    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap) {
        resultMap.put("message", "操作成功");
        resultMap.put("code", 200);
        return resultMap;
    }

    /**
     * 返回失败示例
     *
     * @Param resultMap  返回数据MAP
     * @Return Map<String, Object> 返回数据MAP
     */
    public static Map<String, Object> resultError(Map<String, Object> resultMap) {
        resultMap.put("message", "操作失败");
        resultMap.put("code", 500);
        return resultMap;
    }

    /**
     * 通用示例
     *
     * @Param code 信息码
     * @Param msg  信息
     * @Return Map<String, Object> 返回数据MAP
     */
    public static Map<String, Object> resultCode(Integer code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", msg);
        resultMap.put("code", code);
        return resultMap;
    }

}