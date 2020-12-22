package com.h3c.vdi.viewscreen.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by x19765 on 2020/10/22.
 */

@Data
@ToString
public class RsDomainDev implements Serializable {

    private static final long serialVersionUID = 7915274487164347809L;

    private Long id;

    private Long domainId;

    private Integer devType;

    private String devId;

    private String devName;

    private String vendorId;

    private String vendor;

    private String productId;

    private String product;

    private String ethName;

    private String bus;

    private String device;

    private Integer controller;

    private String driverType;

    private String slot;

    private String function;

    private String pciType;

    private String deviceDriver;
}
