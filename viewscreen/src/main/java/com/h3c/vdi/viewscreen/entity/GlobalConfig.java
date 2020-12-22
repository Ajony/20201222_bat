package com.h3c.vdi.viewscreen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname PARAMETER
 * @Date 2020/7/20 15:03
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_global_config")
@Accessors(chain = true)
public class GlobalConfig implements Serializable {

    private static final long serialVersionUID = -6397424644008758914L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "text")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String text;

    @Column(name = "logic_delete")
    private String logicDelete;

    @Column(name = "reserver")
    private String reserver;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "version")
    private String version;

}
