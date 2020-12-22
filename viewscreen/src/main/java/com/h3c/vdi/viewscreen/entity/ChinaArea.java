package com.h3c.vdi.viewscreen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lgq
 * @since 2020/6/16 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_china_area")
@Accessors(chain = true)
public class ChinaArea implements Serializable {

    private static final long serialVersionUID = 3149454641079956823L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)     //todo 如果想要使用自定义id：setId(Integer id)需要将这行注释一下
    private Long id;

    @Column(name = "address_parent_id")
    private Long addressParentId;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "address_type")
    private Integer addressType;

    @Column(name = "address_code")
    private String addressCode;

    @Column(name = "ext_name")
    private String extName;

    @Column(name = "class_sum")
    private Long classSum;

    @Column(name = "desktop_sum")
    private Long desktopSum;

    @Column(name = "school_sum")
    private Long schoolSum;

    @Column(name = "course_sum")
    private Long courseSum;

    @Column(name = "host_sum")
    private Long hostSum;

    @Column(name = "terminal_sum")
    private Long terminalSum;

    @Column(name = "user_sum")
    private Long userSum;

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
