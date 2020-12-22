package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.NicList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Date 2020/11/9 10:41
 * @Created by lgw2845
 */
public interface NicListDao extends JpaRepository<NicList,Long> {

    NicList findByUuidAndLogicDelete(String uuid, String logicDelete);

    NicList findByNicListContainingAndLogicDelete(String nicList, String logicDelete);
}
