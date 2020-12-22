package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.OnLineInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author lgq
 * @since 2020/6/11 11:20
 */
public interface OnLineInfoDao extends JpaRepository<OnLineInfo, Long> {

//    @Query(value = "SELECT IFNULL(SUM(on_line_desktop_sum),0) AS onLineDesktopSum," +
//            "IFNULL(SUM(on_line_terminal_sum),0) AS onLineTerminalSum," +
//            "IFNULL(SUM(attend_class_sum),0) AS attendClassSum," +
//            "DATE_FORMAT(statistics_date,'%Y-%m-%d') AS statisticsDate " +
//            "FROM tbl_on_line_info " +
//            "WHERE logic_delete = 'n' GROUP BY ?,statistics_date",nativeQuery = true)
//    List<Map<String,Object>> queryProvinceInfo(String code);


    @Query(value = "SELECT IFNULL(SUM(on_line_desktop_sum),0) AS onLineDesktopSum," +
            "IFNULL(SUM(on_line_terminal_sum),0) AS onLineTerminalSum," +
            "IFNULL(SUM(attend_class_sum),0) AS attendClassSum," +
            "DATE_FORMAT(statistics_date,'%Y-%m-%d') AS statisticsDate " +
            "FROM tbl_on_line_info " +
            "WHERE logic_delete = 'n' AND province_code = ? group by statistics_date", nativeQuery = true)
    List<Map<String, Object>> queryProvinceInfo(String code);


    @Query(value = "SELECT IFNULL(SUM(on_line_desktop_sum),0) AS onLineDesktopSum," +
            "IFNULL(SUM(on_line_terminal_sum),0) AS onLineTerminalSum," +
            "IFNULL(SUM(attend_class_sum),0) AS attendClassSum," +
            "DATE_FORMAT(statistics_date,'%Y-%m-%d') AS statisticsDate " +
            "FROM tbl_on_line_info " +
            "WHERE logic_delete = 'n' AND city_code = ? group by statistics_date", nativeQuery = true)
    List<Map<String, Object>> queryCityInfo(String code);


    @Query(value = "SELECT IFNULL(SUM(on_line_desktop_sum),0) AS onLineDesktopSum," +
            "IFNULL(SUM(on_line_terminal_sum),0) AS onLineTerminalSum," +
            "IFNULL(SUM(attend_class_sum),0) AS attendClassSum," +
            "DATE_FORMAT(statistics_date,'%Y-%m-%d') AS statisticsDate " +
            "FROM tbl_on_line_info " +
            "WHERE logic_delete = 'n' AND city_code = ?1 or city_code = ?2 group by statistics_date", nativeQuery = true)
    List<Map<String, Object>> queryCityInfo(String code1,String code2);


    @Query(value = "SELECT IFNULL(SUM(on_line_desktop_sum),0) AS onLineDesktopSum," +
            "IFNULL(SUM(on_line_terminal_sum),0) AS onLineTerminalSum," +
            "IFNULL(SUM(attend_class_sum),0) AS attendClassSum," +
            "DATE_FORMAT(statistics_date,'%Y-%m-%d') AS statisticsDate " +
            "FROM tbl_on_line_info " +
            "WHERE logic_delete = 'n' AND county_code = ? group by statistics_date", nativeQuery = true)
    List<Map<String, Object>> queryCountyInfo(String code);


    @Query(value = "SELECT * from tbl_on_line_info WHERE uuid = ?1 AND logic_delete = 'n' AND DATE_FORMAT(add_date,'%Y-%m-%d') = ?2 ", nativeQuery = true)
    OnLineInfo findByUuidAddDate(String uuid, String now);

    List<OnLineInfo> findByUuidAndLogicDelete(String uuid, String logicDeleteN);


    @Modifying
    @Transactional
    @Query(value = "delete from tbl_on_line_info where logic_delete = 'n' and DATE_FORMAT(add_date,'%Y-%m-%d') not between ?1 and ?2", nativeQuery = true)
    void clearHistoryData(String beginTime, String endTime);


    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);
}
