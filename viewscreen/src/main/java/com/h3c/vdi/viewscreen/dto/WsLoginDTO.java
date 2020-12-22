package com.h3c.vdi.viewscreen.dto;

import lombok.Data;

/**
 * Created by x19765 on 2020/10/17.
 */
@Data
public class WsLoginDTO {
    private String name;
    private String password;

    public WsLoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
