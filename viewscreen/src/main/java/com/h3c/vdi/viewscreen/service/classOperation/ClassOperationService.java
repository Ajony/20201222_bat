package com.h3c.vdi.viewscreen.service.classOperation;

import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.BeginClassReqDTO;
import com.h3c.vdi.viewscreen.dto.EndClassReqDTO;

/**
 * Created by x19765 on 2020/10/14.
 */
public interface ClassOperationService {

    ApiResponse<Boolean> beginClassAction(String uuid, BeginClassReqDTO beginClassReqDTO);

    ApiResponse<Boolean> endClassAction(String uuid, EndClassReqDTO endClassReqDTO);
}
