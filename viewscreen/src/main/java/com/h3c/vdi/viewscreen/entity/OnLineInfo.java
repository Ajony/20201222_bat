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
 * @author lgq  在线和机房利用率统计表（省市
 * @since 2020/6/11 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_on_line_info")
@Accessors(chain = true)
public class OnLineInfo implements Serializable {

    private static final long serialVersionUID = 3206618457699337232L;

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

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "on_line_desktop_sum")
    private Long onLineDesktopSum;

    @Column(name = "on_line_terminal_sum")
    private Long onLineTerminalSum;

    @Column(name = "attend_class_sum")
    private Long attendClassSum;

    @Column(name = "statistics_date")
    private LocalDate statisticsDate;

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
