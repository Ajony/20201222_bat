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

import com.h3c.vdi.viewscreen.entity.StorageColonyInfo;
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
public interface StorageColonyInfoDao extends JpaRepository<StorageColonyInfo, Long> {
    @Query(value = "select * from tbl_storage_colony_info where uuid =?1 and logic_delete = 'n' and DATE_FORMAT(time,'%Y-%m-%d') = ?2", nativeQuery = true)
    List<StorageColonyInfo> findByUuid(String uuid, String datetime);

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_storage_colony_info where uuid = ?1 and logic_delete = 'n' and DATE_FORMAT(add_date,'%Y-%m-%d') not between ?2 and ?3", nativeQuery = true)
    void deleteByAddDate(String uuid, String beginTime, String endTime);

    @Query(value = "select * from tbl_storage_colony_info  where uuid = ?1 and logic_delete = 'n' and DATE_FORMAT(time,'%Y-%m-%d') between ?2 and ?3", nativeQuery = true)
    List<StorageColonyInfo> findByUuidAndTime(String uuid, String beginTime, String endTime);

    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);
}