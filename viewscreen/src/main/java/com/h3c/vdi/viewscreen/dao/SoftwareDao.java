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

import com.h3c.vdi.viewscreen.entity.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/27
 * @since 1.0.0
 */
public interface SoftwareDao extends JpaRepository<Software, Long> {
    @Query(value = "select * from tbl_software where uuid = ?1 and logic_delete = 'n' ORDER BY (software_index + 0) DESC", nativeQuery = true)
    List<Software> findByUuid(String uuid);

    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);

}