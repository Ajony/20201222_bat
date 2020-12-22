package com.h3c.vdi.viewscreen.api.model.response.computenode;

import lombok.Data;

import java.io.Serializable;

@Data
public class CloudOSResult<D> implements Serializable {

    private static final long serialVersionUID = 468159520720769317L;

    private String code;

    private String msg;

    private D data;

    private int total;

    public CloudOSResult(D data) {
        this.data = data;
    }

    public CloudOSResult() {

    }
}
