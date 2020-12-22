package com.h3c.vdi.viewscreen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lgq
 * @since 2020/6/17 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_share_area")
@Accessors(chain = true)
public class ShareArea implements Serializable {

    private static final long serialVersionUID = 3816369697116207689L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_code")
    private String provinceCode;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "county_code")
    private String countyCode;

    @Column(name = "county_name")
    private String countyName;

    @Column(name = "college")
    private String college;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "ip")
    private String ip;

    @Column(name = "ip_list")
    private String ipList;

    @Column(name = "scene")
    private String scene;

    @Column(name = "status")
    private String status;

    @Column(name = "location")
    private String location;

    @Column(name = "online_date")
    private LocalDate onlineDate;

    @Column(name = "class_sum")
    private Long classSum;

    @Column(name = "desktop_sum")
    private Long desktopSum;

    @Column(name = "course_sum")
    private Long courseSum;

    @Column(name = "user_sum")
    private Long userSum;

    @Column(name = "terminal_sum")
    private Long terminalSum;

    @Column(name = "host_sum")
    private Long hostSum;

    @Column(name = "attend_class_sum")
    private Long attendClassSum;

    @Column(name = "attend_class_time")
    private Long attendClassTime;

    @Column(name = "logic_delete")
    private String logicDelete;

    @Column(name = "reserver1")
    private String reserver1;

    @Column(name = "reserver2")
    private String reserver2;

    @Column(name = "reserver3")
    private String reserver3;

    @Column(name = "reserver4")
    private String reserver4;

    @Column(name = "reserver5")
    private String reserver5;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "add_by")
    private String addBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "delete_by")
    private String deleteBy;

    @Column(name = "version")
    private String version;


}
