/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: HostStatusCount
 * Author:   ykf8829
 * Date:     2020/5/27 17:56
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
 * @create 2020/5/27
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_college_info")
@Accessors(chain = true)
public class CollegeInfo implements Serializable {

    private static final long serialVersionUID = -5100265443681460403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "syestemlevel")
    private String syestemlevel;

    @Column(name = "user_sum")
    private Long userSum;

    @Column(name = "desktop_total")
    private Long desktopTotal;

    @Column(name = "desktop_running_sum")
    private Long desktopRunningSum;

    @Column(name = "desktop_pause_sum")
    private Long desktopPauseSum;

    @Column(name = "desktop_close_sum")
    private Long desktopCloseSum;

    @Column(name = "desktop_error_sum")
    private Long desktopErrorSum;

    @Column(name = "desktop_unknow_sum")
    private Long desktopUnknowSum;

    @Column(name = "desktop_running_ls_sum")
    private Long desktopRunningLsSum;

    @Column(name = "desktop_pause_ls_sum")
    private Long desktopPauseLsSum;

    @Column(name = "desktop_close_ls_sum")
    private Long desktopCloseLsSum;

    @Column(name = "desktop_error_ls_sum")
    private Long desktopErrorLsSum;

    @Column(name = "desktop_unknow_ls_sum")
    private Long desktopUnknowLsSum;

    @Column(name = "desktop_max")
    private Long desktopMax;

    @Column(name = "terminal_max")
    private Long terminalMax;

    @Column(name = "terminal_total")
    private Long terminalTotal;

    @Column(name = "terminal_online_sum")
    private Long terminalOnlineSum;

    @Column(name = "terminal_offline_sum")
    private Long terminalOfflineSum;

    @Column(name = "terminal_vdi_sum")
    private Long terminalVdiSum;

    @Column(name = "terminal_idv_sum")
    private Long terminalIdvSum;

    @Column(name = "terminal_voi_sum")
    private Long terminalVoiSum;

    @Column(name = "terminal_usetime")
    private Long terminalUsetime;

    @Column(name = "vm_total")
    private Long vmTotal;

    @Column(name = "vm_running_sum")
    private Long vmRunningSum;

    @Column(name = "vm_error_sum")
    private Long vmErrorSum;

    @Column(name = "vm_repair_sum")
    private Long vmRepairSum;

    @Column(name = "classroom_sum")
    private Long classroomSum;

    @Column(name = "course_sum")
    private Long courseSum;

    @Column(name = "host_sum")
    private Long hostSum;

    @Column(name = "host_nomal_sum")
    private Long hostNomalSum;

    @Column(name = "host_closed_sum")
    private Long hostClosedSum;

    @Column(name = "host_maintain_sum")
    private Long hostMaintainSum;

    @Column(name = "class_time")
    private Long classTime;

    @Column(name = "class_count")
    private Long classCount;

    @Column(name = "class_count_per_day")
    private Long classCountPerDay;

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
