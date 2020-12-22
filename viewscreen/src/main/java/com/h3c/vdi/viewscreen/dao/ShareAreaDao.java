package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.ShareArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author lgq
 * @since 2020/6/17 15:10
 */
public interface ShareAreaDao extends JpaRepository<ShareArea, Long> {

    @Query(value = "select uuid from tbl_share_area where logic_delete = 'n' AND college = ?1", nativeQuery = true)
    String findUuidByCollegeName(String college);

    ShareArea findByUuidAndLogicDelete(String uuid, String logicDelete);

    ShareArea findByUuidAndLocationAndLogicDelete(String uuid, String location, String logicDelete);

    @Query(value = "SELECT IFNULL(SUM(attend_class_time),0) AS timeAll FROM tbl_share_area WHERE logic_delete = 'n' AND attend_class_time IS NOT NULL", nativeQuery = true)
    Long timeAll();


    /**
     * </-----------------------------------------[省市]----------------------------------->
     */

    @Query(value = "SELECT COUNT( 1 ) AS schoolSum,province_code AS addressCode,province_name AS addressName " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n'" +
            "GROUP BY province_code", nativeQuery = true)
    List<Map<String, Object>> provinceSchoolSum();

    @Query(value = "SELECT province_code AS addressCode," +
            "province_name AS addressName," +
            "IFNULL(SUM( class_sum ), 0 ) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' " +
            "GROUP BY province_code", nativeQuery = true)
    List<Map<String, Object>> provinceRestSum();

    @Query(value = "SELECT \n" +
            "            IFNULL(SUM( class_sum ), 0 ) AS classSum,\n" +
            "            IFNULL(SUM( desktop_sum ),0) AS desktopSum,\n" +
            "            IFNULL(SUM( course_sum ),0) AS courseSum,\n" +
            "            IFNULL(SUM( user_sum ),0) AS userSum,\n" +
            "            IFNULL(SUM( terminal_sum ),0) AS terminalSum,\n" +
            "            IFNULL(SUM( host_sum ),0) AS hostSum \n" +
            "            FROM tbl_share_area\n" +
            "            WHERE logic_delete = 'n' \n" +
            "            AND province_code = ?", nativeQuery = true)
    Map<String, Object> findRestSum(String addressCode);


    /**
     * </-----------------------------------------[市区]----------------------------------->
     */

    @Query(value = "SELECT COUNT( 1 ) AS schoolSum,city_code AS addressCode,city_name AS addressName " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n'" +
            "GROUP BY province_code,city_code", nativeQuery = true)
    List<Map<String, Object>> citySchoolSum();

    @Query(value = "SELECT city_code AS addressCode," +
            "city_name AS addressName," +
            "IFNULL( SUM( class_sum ), 0 ) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' " +
            "GROUP BY province_code,city_code", nativeQuery = true)
    List<Map<String, Object>> cityRestSum();


    /**
     * </-----------------------------------------[区县]----------------------------------->
     */


    @Query(value = "SELECT COUNT( 1 ) AS schoolSum,county_code AS addressCode,county_name AS addressName " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n'" +
            "GROUP BY province_code,city_code,county_code", nativeQuery = true)
    List<Map<String, Object>> countySchoolSum();


    @Query(value = "SELECT county_code AS addressCode," +
            "county_name AS addressName," +
            "IFNULL( SUM( class_sum ), 0 ) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' " +
            "GROUP BY province_code,city_code,county_code", nativeQuery = true)
    List<Map<String, Object>> countyRestSum();

    /**
     * </-----------------------------------------[END]----------------------------------->
     */


    @Query(value = "SELECT IFNULL((SELECT college FROM tbl_share_area WHERE uuid = ? AND logic_delete = 'n'),'null') as college", nativeQuery = true)
    String findCollegeName(String uuid);


    /**
     * </-----------------------------------------[省市]----------------------------------->
     */
    List<ShareArea> findByProvinceCodeAndLogicDelete(String code, String logicDeleteN);

    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND province_code = ? " +
            "ORDER BY attend_class_sum DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryProvinceAttendClassSum(String code);


    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND province_code = ? " +
            "ORDER BY attend_class_time DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryProvinceAttendClassTime(String code);


    /**
     * </-----------------------------------------[市区]----------------------------------->
     */
    List<ShareArea> findByCityCodeAndLogicDelete(String code, String logicDeleteN);

    List<ShareArea> findByCityCodeOrCityCodeAndLogicDelete(String code1, String code2, String logicDeleteN);

    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ? " +
            "ORDER BY attend_class_sum DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryCityAttendClassSum(String code);


    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ?1 or city_code = ?2 " +
            "ORDER BY attend_class_sum DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryCityAttendClassSum(String code1, String code2);


    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ? " +
            "ORDER BY attend_class_time DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryCityAttendClassTime(String code);


    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ?1 or city_code = ?2 " +
            "ORDER BY attend_class_time DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryCityAttendClassTime(String code1, String code2);


    /**
     * </-----------------------------------------[区县]----------------------------------->
     */
    List<ShareArea> findByCountyCodeAndLogicDelete(String code, String logicDeleteN);

    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND county_code = ? " +
            "ORDER BY attend_class_sum DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryCountyAttendClassSum(String code);


    @Query(value = "SELECT college AS college," +
            "attend_class_sum AS attendClassSum," +
            "attend_class_time AS attendClassTime " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND county_code = ? " +
            "ORDER BY attend_class_time DESC LIMIT 8", nativeQuery = true)
    List<Map<String, Object>> queryCountyAttendClassTime(String code);


    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n'", nativeQuery = true)
    Map<String, Object> queryNationwide();

    @Query(value = "SELECT IFNULL(( SELECT COUNT(1) FROM tbl_share_area WHERE logic_delete = 'n'),0) AS schoolSum", nativeQuery = true)
    Long queryNationwideSchoolSum();


    @Query(value = "SELECT * FROM tbl_share_area WHERE logic_delete = 'n' AND province_code = ? GROUP BY province_code,city_code", nativeQuery = true)
    List<ShareArea> findProvinceAndCity(String addressCode);


    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND province_code = ?", nativeQuery = true)
    Map<String, Object> findProvinceSum(String code);


    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ?1 or city_code = ?2", nativeQuery = true)
    Map<String, Object> findCitySum(String code1, String code2);

    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND county_code = ?", nativeQuery = true)
    Map<String, Object> findCountySum(String code);


    @Query(value = "SELECT IFNULL(( SELECT COUNT(1) FROM tbl_share_area WHERE logic_delete = 'n' and province_code = ?),0) AS schoolSum", nativeQuery = true)
    Long findProvinceSchoolSum(String code);

    @Query(value = "SELECT IFNULL(( SELECT COUNT(1) FROM tbl_share_area WHERE logic_delete = 'n' and city_code = ?1 or city_code = ?2),0) AS schoolSum", nativeQuery = true)
    Long findCitySchoolSum(String code1, String code2);

    @Query(value = "SELECT IFNULL(( SELECT COUNT(1) FROM tbl_share_area WHERE logic_delete = 'n' and county_code = ?),0) AS schoolSum", nativeQuery = true)
    Long findCountySchoolSum(String code);


    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND province_code = ?1 AND city_code = ?2", nativeQuery = true)
    Map<String, Object> findCityAllSum(String provinceCode, String cityCode);


    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ?1 AND county_code = ?2", nativeQuery = true)
    Map<String, Object> findCountyAllSum(String cityCode, String countyCode);

    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_share_area " +
            "WHERE logic_delete = 'n' AND city_code = ?1 or city_code = ?2 AND county_code = ?3", nativeQuery = true)
    Map<String, Object> findCountyAllSum(String cityCode1, String cityCode2, String countyCode);


    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);

    ShareArea findByIdAndLogicDelete(Long id, String logicDelete);


    @Modifying
    @Transactional
    @Query(value = "update tbl_share_area set ip = ?1, reserver1 = ?2 where uuid = ?3 and logic_delete = 'n'", nativeQuery = true)
    void updIp(String ip, String str, String uuid);

    @Modifying
    @Transactional
    @Query(value = "update tbl_share_area set status = ?1, reserver2 = ?2 where uuid = ?3 and logic_delete = 'n'", nativeQuery = true)
    void reportSwitch(String status, String str, String uuid);

}
