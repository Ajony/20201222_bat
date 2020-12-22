/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CollegeInfoDao
 * Author:   ykf8829
 * Date:     2020/5/27 19:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.dao;

import com.h3c.vdi.viewscreen.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/27
 * @since 1.0.0
 */
public interface ClassroomDao extends JpaRepository<Classroom, Long> {

    @Query(value = "select * from tbl_classroom where logic_delete = 'n'", nativeQuery = true)
    List<Classroom> findAll();

    @Query(value = "select * FROM tbl_classroom " +
            "WHERE uuid = ?1 and DATE_FORMAT(date ,'%Y-%m') = ?2 AND logic_delete = 'n'", nativeQuery = true)
    Classroom queryClassroom(String uuid, String toString);

    @Query(value="select * from tbl_classroom c where c.uuid = ?1 and c.logic_delete ='n' and DATE_FORMAT(c.date ,'%Y-%m') between ?3 and ?2",nativeQuery = true)
    List<Classroom> byUuidAndDate(String uuid,String endTime ,String beginDate);

    @Modifying
    @Transactional
    void deleteByUuidAndLogicDelete(String uuid, String var);
}
