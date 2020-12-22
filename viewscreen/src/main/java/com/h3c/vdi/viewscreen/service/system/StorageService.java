/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: StorageColonyInfoService
 * Author:   ykf8829
 * Date:     2020/6/17 18:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.system;

import com.h3c.vdi.viewscreen.api.model.response.college.StorageDto;
import com.h3c.vdi.viewscreen.api.model.response.storage.ResponseData;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/17
 * @since 1.0.0
 */
public interface StorageService {
    ApiResponse<List<StorageDto>> storageList(String uuid);

    ApiResponse<ResponseData> ioPs(String uuid);

    ApiResponse<ResponseData> io(String uuid);
}
