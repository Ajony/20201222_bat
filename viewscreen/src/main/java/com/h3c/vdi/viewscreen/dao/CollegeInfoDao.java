/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeIngoDao
 * Author:   ykf8829
 * Date:     2020/6/23 18:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.CollegeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/23
 * @since 1.0.0
 */
public interface CollegeInfoDao extends JpaRepository<CollegeInfo, Long> {
    @Query(value = "select * from tbl_college_info c where c.uuid = ?1 and c.logic_delete ='n' and DATE_FORMAT(c.add_date ,'%Y-%m-%d') between ?3 and ?2", nativeQuery = true)
    List<CollegeInfo> queryByUuidAndAddDate(String uuid, String endTime, String beginTime);


    @Query(value = "select * FROM tbl_college_info " +
            "WHERE uuid = ?1 and DATE_FORMAT(add_date,'%Y-%m-%d') = ?2 AND logic_delete = 'n'", nativeQuery = true)
    CollegeInfo queryCollegeInfo(String uuid, String toString);

    CollegeInfo findByUuidAndLogicDelete(String uuid, String logicDelete);


    //    @Query(value = "SELECT uuid AS uuid,classroom_sum AS classSum,desktop_total AS desktopSum," +
//            "course_sum AS courseSum,user_sum AS userSum,terminal_total AS terminalSum," +
//            "host_sum AS hostSum,class_count AS attendClassSum,class_time AS attendClassTime " +
//            "FROM tbl_college_info " +
//            "WHERE logic_delete = 'n' AND DATE_FORMAT(add_date,'%Y-%m-%d') = ? ", nativeQuery = true)
//    List<Map<String, Object>> queryYesterdayInfo(String yesterday);
    @Query(value = "SELECT uuid AS uuid,classroom_sum AS classSum,desktop_total AS desktopSum," +
            "course_sum AS courseSum,user_sum AS userSum,terminal_total AS terminalSum," +
            "host_sum AS hostSum,class_count AS attendClassSum,class_time AS attendClassTime " +
            "FROM tbl_college_info " +
            "WHERE logic_delete = 'n' ", nativeQuery = true)
    List<Map<String, Object>> queryYesterdayInfo();


    @Query(value = "SELECT t1.uuid,t2.province_code AS provinceCode," +
            "t2.province_name AS provinceName," +
            "t2.city_code AS cityCode," +
            "t2.city_name AS cityName," +
            "t2.county_code AS countyCode," +
            "t2.county_name AS countyName " +
            "FROM tbl_college_info t1,tbl_share_area t2 " +
            "WHERE t1.uuid = t2.uuid AND t1.logic_delete = 'n' AND t2.logic_delete = 'n' " +
            "GROUP BY t2.uuid", nativeQuery = true)
    List<Map<String, Object>> queryUuidList();

    @Query(value = "SELECT IFNULL(desktop_running_sum,0)+IFNULL(desktop_running_ls_sum,0) AS onLineDesktopSum," +
            "terminal_online_sum AS onLineTerminalSum," +
            "class_count_per_day AS attendClassSum," +
            "DATE_FORMAT(add_date,'%Y-%m-%d') AS statisticsDate " +
            "FROM tbl_college_info WHERE logic_delete = 'n' " +
            "AND uuid = ?3 AND DATE_FORMAT(add_date,'%Y-%m-%d') > ?1 AND DATE_FORMAT(add_date,'%Y-%m-%d') <= ?2 " +
            "ORDER BY add_date", nativeQuery = true)
    List<Map<String, Object>> queryNearTenData(String beginTime, String endTime, String uuid);

    //List<CollegeInfo> queryByUuidAndAddDate(String Uuid,String)

    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);
}