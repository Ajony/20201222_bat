package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname RoleDao
 * @Date 2020/7/21 11:39
 * @Created by lgw2845
 */
public interface RoleDao extends JpaRepository<Role,Long> {
}
