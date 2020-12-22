package com.h3c.vdi.viewscreen.service;

import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dto.SubTitleDTO;

/**
 * Created by x19765 on 2020/10/15.
 */
public interface SubTitleService {

    ApiResponse<SubTitleDTO> getSubTitle();

    ApiResponse<Boolean> editSubTitle(SubTitleDTO subTitleDTO);

    void updateByName(String name, String value);

}
