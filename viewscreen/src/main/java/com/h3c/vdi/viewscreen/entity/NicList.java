package com.h3c.vdi.viewscreen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Date 2020/11/9 10:06
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_nic_list")
@Accessors(chain = true)
public class NicList implements Serializable {

    private static final long serialVersionUID = -478853103110978387L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nic_list")
    private String nicList;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "logic_delete")
    private String logicDelete;

    @Column(name = "reserver")
    private String reserver;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
