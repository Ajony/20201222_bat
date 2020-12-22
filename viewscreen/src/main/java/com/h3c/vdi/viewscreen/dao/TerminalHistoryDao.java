package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.TerminalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Date 2020/10/27 14:07
 * @Created by lgw2845
 */
public interface TerminalHistoryDao extends JpaRepository<TerminalHistory, Long> {

    @Query(value = "select * FROM tbl_terminal_history " +
            "WHERE uuid = ?1 and DATE_FORMAT(add_date,'%Y-%m-%d') = ?2 AND logic_delete = 'n'", nativeQuery = true)
    TerminalHistory queryHistory(String uuid, String toString);

    List<TerminalHistory> findByUuidAndLogicDeleteOrderByAddDateAsc(String uuid, String logicDeleteN);

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_terminal_history where logic_delete = 'n' and DATE_FORMAT(add_date,'%Y-%m-%d') not between ?1 and ?2", nativeQuery = true)
    void clearHistoryData(String beginTime, String endTime);


    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);
}
