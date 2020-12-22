package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.ChinaArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author lgq
 * @since 2020/6/16 15:18
 */
public interface ChinaAreaDao extends JpaRepository<ChinaArea, Long> {
    ChinaArea findByAddressCodeAndAddressTypeAndLogicDelete(String addressCode, Integer addressType, String logicDelete);

    ChinaArea findByAddressParentIdAndAddressTypeAndLogicDelete(Long id, Integer type, String logicDelete);

    ChinaArea findByAddressTypeAndAddressCodeAndLogicDelete(Integer type, String code, String logicDeleteN);

    List<ChinaArea> findByAddressTypeAndAddressCodeOrAddressCodeAndLogicDelete(Integer type, String code1, String code2, String logicDeleteN);

//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE tbl_china_area " +
//            "SET class_sum = NULL," +
//            "desktop_sum = NULL," +
//            "school_sum = NULL," +
//            "course_sum = NULL," +
//            "host_sum = NULL," +
//            "terminal_sum = NULL," +
//            "user_sum = NULL," +
//            "modified_date = NULL " +
//            "WHERE logic_delete = 'n' AND address_type != 0", nativeQuery = true)
//    void clearHistoryData();


    @Query(value = "SELECT " +
            "IFNULL(SUM( class_sum ),0) AS classSum," +
            "IFNULL(SUM( desktop_sum ),0) AS desktopSum," +
            "IFNULL(SUM( school_sum ),0) AS schoolSum," +
            "IFNULL(SUM( course_sum ),0) AS courseSum," +
            "IFNULL(SUM( host_sum ),0) AS hostSum," +
            "IFNULL(SUM( terminal_sum ),0) AS terminalSum," +
            "IFNULL(SUM( user_sum ),0) AS userSum " +
            "FROM tbl_china_area " +
            "WHERE address_type = 1 AND address_code != 0 AND logic_delete = 'n'", nativeQuery = true)
    Map<String, Object> summation();


//    @Query(value = "SELECT * FROM tbl_china_area " +
//            "WHERE address_type = 1 " +
//            "AND logic_delete = 'n' " +
//            "AND address_code != 0 " +
//            "AND modified_date IS NOT NULL " +
//            "ORDER BY school_sum DESC", nativeQuery = true)
//    List<ChinaArea> queryArea();

    @Query(value = "SELECT * FROM tbl_china_area " +
            "WHERE address_type = 1 " +
            "AND logic_delete = 'n' " +
            "AND address_code != 0 " +
            "ORDER BY school_sum DESC", nativeQuery = true)
    List<ChinaArea> queryArea();

    List<ChinaArea> findByAddressParentIdAndLogicDelete(Long addressParentId, String logicDelete);

    List<ChinaArea> findByAddressParentIdOrAddressParentIdAndLogicDelete(Long var1, Long var2, String logicDelete);


    List<ChinaArea> findByAddressTypeAndLogicDelete(Integer type, String logicDeleteN);

    List<ChinaArea> findByAddressCodeAndLogicDelete(String addressCode, String logicDeleteN);


}
