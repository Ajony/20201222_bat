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

import com.h3c.vdi.viewscreen.entity.VersionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/5/27
 * @since 1.0.0
 */
public interface VersionInfoDao extends JpaRepository<VersionInfo, Long> {

}
