package com.h3c.vdi.viewscreen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Date 2020/10/27 11:54
 * @Created by lgw2845
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_terminal_history")
@Accessors(chain = true)
public class TerminalHistory implements Serializable {

    private static final long serialVersionUID = 6162773075754579006L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "terminal_total")
    private Long terminalTotal;

    @Column(name = "terminal_online_sum")
    private Long terminalOnlineSum;

    @Column(name = "terminal_use_time")
    private Long terminalUseTime;

    @Column(name = "logic_delete")
    private String logicDelete;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;


}
