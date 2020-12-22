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

import com.h3c.vdi.viewscreen.entity.CollegeLastDynamic;
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
public interface CollegeLastDynamicDao extends JpaRepository<CollegeLastDynamic, Long> {

    @Query(value = "SELECT t2.* FROM tbl_share_area t1,tbl_college_last_dynamic t2 " +
            "WHERE t1.uuid = t2.uuid AND t1.province_code = ? AND t1.logic_delete = 'n' AND t2.logic_delete = 'n' " +
            "ORDER BY t2.id DESC LIMIT 5", nativeQuery = true)
    List<CollegeLastDynamic> queryProvinceLastDynamic(String code);


    @Query(value = "SELECT t2.* FROM tbl_share_area t1,tbl_college_last_dynamic t2 " +
            "WHERE t1.uuid = t2.uuid AND t1.city_code = ? AND t1.logic_delete = 'n' AND t2.logic_delete = 'n' " +
            "ORDER BY t2.id DESC LIMIT 5", nativeQuery = true)
    List<CollegeLastDynamic> queryCityLastDynamic(String code);


    @Query(value = "SELECT t2.* FROM tbl_share_area t1,tbl_college_last_dynamic t2 " +
            "WHERE t1.uuid = t2.uuid AND t1.city_code = ?1 or t1.city_code = ?2 AND t1.logic_delete = 'n' AND t2.logic_delete = 'n' " +
            "ORDER BY t2.id DESC LIMIT 5", nativeQuery = true)
    List<CollegeLastDynamic> queryCityLastDynamic(String code1, String code2);


    @Query(value = "SELECT t2.* FROM tbl_share_area t1,tbl_college_last_dynamic t2 " +
            "WHERE t1.uuid = t2.uuid AND t1.county_code = ? AND t1.logic_delete = 'n' AND t2.logic_delete = 'n' " +
            "ORDER BY t2.id DESC LIMIT 5", nativeQuery = true)
    List<CollegeLastDynamic> queryCountyLastDynamic(String code);


    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);
}
