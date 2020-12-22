package com.h3c.vdi.viewscreen.service.bureaudot;

import com.h3c.vdi.viewscreen.api.model.response.bureaudot.ResponsePageBureauDot;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.api.result.page.ApiPage;

/**
 * @Date 2020/11/3 14:07
 * @Created by lgw2845
 */
public interface BureauDotService {


//    ApiResponse<Boolean> delete(String id);

    ApiResponse<Boolean> deleteByIds(String ids);

    ApiResponse<ApiPage<ResponsePageBureauDot>> page(Integer current, Integer size, String provinceName, String cityName, String countyName, String college);

    ApiResponse<ApiPage<ResponsePageBureauDot>> homoPage(Integer current, Integer size);
}
