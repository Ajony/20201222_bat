package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname UserDao
 * @Date 2020/7/21 11:38
 * @Created by lgw2845
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsernameAndLogicDelete(String username, String logicDelete);
}
