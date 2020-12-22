package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.TotalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author lgq
 * @since 2020/6/11 11:20
 */
public interface TotalTimeDao extends JpaRepository<TotalTime, Long> {

    TotalTime findByLogicDelete(String logicDeleteN);

    @Query(value = "SELECT * FROM tbl_total_time WHERE logic_delete = 'n' LIMIT 1", nativeQuery = true)
    TotalTime find();

}
