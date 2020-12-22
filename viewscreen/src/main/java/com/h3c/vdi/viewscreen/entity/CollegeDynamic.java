/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeDynamic
 * Author:   ykf8829
 * Date:     2020/6/1 15:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/1
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_college_dynamic")
@Accessors(chain = true)
public class CollegeDynamic implements Serializable {
    private static final long serialVersionUID = -5100265443681460403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host_number")
    private Long hostNumber;

    @Column(name = "classroom_number")
    private Long classroomNumber;

    @Column(name = "terminal_number")
    private Long terminalNumber;

    @Column(name = "online_date")
    private LocalDate onlineDate;

    @Column(name = "uuid")
    private String uuid;

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
