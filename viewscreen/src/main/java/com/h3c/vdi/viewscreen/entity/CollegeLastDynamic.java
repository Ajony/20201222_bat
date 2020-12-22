/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeLastDynamic
 * Author:   ykf8829
 * Date:     2020/5/28 17:21
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
import java.time.LocalDateTime;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/28
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_college_last_dynamic")
@Accessors(chain = true)
public class CollegeLastDynamic implements Serializable {
    private static final long serialVersionUID = -5100265443681460403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name = "classroom_status")
    private Integer classroomStatus;

    @Column(name = "class_begin_time")
    private  LocalDateTime classBeginTime;

    @Column(name = "class_over_time")
    private LocalDateTime classOverTime;

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
