package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.LogoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Date 2020/10/20 11:42
 * @Created by lgw2845
 */
public interface LogoCustomDao extends JpaRepository<LogoCustom, Long> {

    @Query(value = "SELECT * FROM tbl_logo_custom WHERE logic_delete = 'n' LIMIT 1", nativeQuery = true)
    LogoCustom showPicture();
}
