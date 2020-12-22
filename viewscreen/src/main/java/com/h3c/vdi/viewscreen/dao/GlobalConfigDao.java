package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.GlobalConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Classname GlobalConfigDao
 * @Date 2020/7/20 15:20
 * @Created by lgw2845
 */
public interface GlobalConfigDao extends JpaRepository<GlobalConfig,Long> {

    @Query(value = "SELECT * FROM tbl_global_config WHERE logic_delete = 'n' LIMIT 1", nativeQuery = true)
    GlobalConfig find();
}
