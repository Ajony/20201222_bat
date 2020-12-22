/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeInfoDao
 * Author:   ykf8829
 * Date:     2020/5/27 19:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.CollegeDynamic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/27
 * @since 1.0.0
 */
public interface CollegeDynamicDao extends JpaRepository<CollegeDynamic, Long> {

    CollegeDynamic findByUuidAndLogicDelete(String uuid, String logicDeleteN);

    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);


    CollegeDynamic findByUuidAndOnlineDateAndHostNumberAndClassroomNumberAndTerminalNumberAndLogicDelete(String uuid, LocalDate onlineDate, Long hostNumber, Long classroomNumber, Long terminalNumber, String logicDeleteN);
}
