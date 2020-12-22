package com.h3c.vdi.viewscreen.api.result;

/**
 * @author lgq
 * @since 2020-5-25
 */
public enum ApiErrorEnum implements IErrorEnum<Integer> {

    S00001(500, "系统异常:%s！"),
    S40001(401, "未找到登录信息，请重新登录:%s！"),
    S40003(403, "未授权:%s！"),

    E00001(1, "没有查询到%s数据！"),
    E00002(2, "新增%s数据失败！"),
    E00003(3, "修改%s数据失败！"),
    E00004(4, "删除%s数据失败！"),
    E00100(100, "参数错误:%s！"),
    E00101(101, "缺少参数:%s！"),
    E10000(10000, "后台服务系统错误:%s！"),
    E20000(20000, "空指针异常:%s！"),
    E30000(20000, "JSON数据解析异常:%s！"),
    E50000(50000, "上传文件失败！"),
    E50101(50101, "参数%s为空！"),
    E50102(50102, "文件过大，大于%s！"),
    E50103(50103, "未找到登录信息，请重新登录:%s！"),
    E50104(50104, "用户名或密码输入错误，请重新登录！");

    private final Integer value;
    private final String message;

    ApiErrorEnum(final int value, final String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public static String getMessageByValue(Integer value) {
        if (E00001.getValue().equals(value)) {
            return E00001.getMessage();
        } else if (E00002.getValue().equals(value)) {
            return E00002.getMessage();
        } else if (E00003.getValue().equals(value)) {
            return E00003.getMessage();
        } else if (E00004.getValue().equals(value)) {
            return E00004.getMessage();
        } else if (E00100.getValue().equals(value)) {
            return E00100.getMessage();
        } else if (E00101.getValue().equals(value)) {
            return E00101.getMessage();
        } else if (E10000.getValue().equals(value)) {
            return E10000.getMessage();
        } else if (E20000.getValue().equals(value)) {
            return E20000.getMessage();
        } else if (E30000.getValue().equals(value)) {
            return E30000.getMessage();
        } else if (S00001.getValue().equals(value)) {
            return S00001.getMessage();
        } else if (S40001.getValue().equals(value)) {
            return S40001.getMessage();
        } else if (S40003.getValue().equals(value)) {
            return S40003.getMessage();
        }else if (E50000.getValue().equals(value)) {
            return E50000.getMessage();
        }else if (E50101.getValue().equals(value)) {
            return E50101.getMessage();
        }else if (E50102.getValue().equals(value)) {
            return E50102.getMessage();
        }else if (E50103.getValue().equals(value)) {
            return E50102.getMessage();
        }

        return "";
    }

    public ApiErrorEnum getErrorEnumByName(String name) {
        if (E00001.getName().equals(name)) {
            return E00001;
        } else if (E00002.getName().equals(name)) {
            return E00002;
        } else if (E00003.getName().equals(name)) {
            return E00003;
        } else if (E00004.getName().equals(name)) {
            return E00004;
        } else if (E00100.getName().equals(name)) {
            return E00100;
        } else if (E00101.getName().equals(name)) {
            return E00101;
        } else if (E10000.getName().equals(name)) {
            return E10000;
        } else if (E20000.getName().equals(name)) {
            return E20000;
        } else if (E30000.getName().equals(name)) {
            return E30000;
        } else if (S00001.getName().equals(name)) {
            return S00001;
        } else if (S40001.getName().equals(name)) {
            return S40001;
        } else if (S40003.getName().equals(name)) {
            return S40003;
        }else if (E50000.getName().equals(name)) {
            return E50000;
        }else if (E50101.getName().equals(name)) {
            return E50101;
        }else if (E50102.getName().equals(name)) {
            return E50102;
        }else if(E50103.getName().equals(name)) {
            return E50103;
        }

        return ApiErrorEnum.E00001;
    }
}