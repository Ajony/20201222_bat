package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.SubTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by x19765 on 2020/10/15.
 */
public interface SubTitleDao extends JpaRepository<SubTitle, Long> {

    @Query(value = "select * from tbl_subtitle where name =?1", nativeQuery = true)
    List<SubTitle> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "update tbl_subtitle set value = ?1 where name =?2", nativeQuery = true)
    void updateByName(String value, String name);
}
