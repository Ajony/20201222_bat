package com.h3c.vdi.viewscreen.service.auth;

import com.h3c.vdi.viewscreen.api.model.response.globalconfig.ResponseGlobalConfig;
import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponseChinaArea;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.ChinaAreaDao;
import com.h3c.vdi.viewscreen.dao.GlobalConfigDao;
import com.h3c.vdi.viewscreen.entity.ChinaArea;
import com.h3c.vdi.viewscreen.entity.GlobalConfig;
import com.h3c.vdi.viewscreen.security.basic.ResponseUserDetails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname GlobalConfigServiceImpl
 * @Date 2020/7/20 15:19
 * @Created by lgw2845
 */
@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {

    @Resource
    GlobalConfigDao globalConfigDao;

    @Resource
    ChinaAreaDao chinaAreaDao;

    private static final String ERROR_MSG = "初始化区域失败，已初始化区域！";
    private static final String TYPE = "init_conf";
    private static final String NAME = "init.area";
    private static final String RESERVE = "初始化地区类型：0/全国，1/区域";

    @Override
    public ApiResponse<Boolean> initConfig(String value, String text) {
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00002, "");
        if (StringUtils.isNotBlank(value)) {
            if (CollectionUtils.isNotEmpty(globalConfigDao.findAll())) {
                returnValue = ApiResponse.buildError(ApiErrorEnum.S00001, ERROR_MSG);
            } else {
                GlobalConfig globalConfig = new GlobalConfig();
                globalConfig.setAddDate(LocalDateTime.now());
                globalConfig.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                globalConfig.setType(TYPE);
                globalConfig.setName(NAME);
                globalConfig.setValue(value);
                globalConfig.setText(text);
                globalConfig.setReserver(RESERVE);
                globalConfigDao.save(globalConfig);
                returnValue = ApiResponse.buildSuccess(true);
            }
        }
        return returnValue;
    }


    @Override
    public ApiResponse<List<ResponseChinaArea>> queryArea(String addressCode) {
        ApiResponse<List<ResponseChinaArea>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");

        if (StringUtils.isNotBlank(addressCode)) {
            ChinaArea chinaArea = chinaAreaDao.findByAddressCodeAndLogicDelete(addressCode, Constant.LogicDelete.LOGIC_DELETE_N).get(0);
            if (Objects.nonNull(chinaArea)) {

                Long var1 = chinaArea.getId();
                Long var2 = (long) -1;

                if (chinaArea.getAddressCode().equals("110000") ||
                        chinaArea.getAddressCode().equals("120000") ||
                        chinaArea.getAddressCode().equals("310000") ||
                        chinaArea.getAddressCode().equals("500000") ||
                        chinaArea.getAddressCode().equals("810000") ||
                        chinaArea.getAddressCode().equals("820000")) {
                    List<ChinaArea> area = chinaAreaDao.findByAddressParentIdAndLogicDelete(var1, Constant.LogicDelete.LOGIC_DELETE_N);
                    if (CollectionUtils.isNotEmpty(area)) {
                        var1 = area.get(0).getId();
                        if (area.size()>1){
                            var2 = area.get(1).getId();
                        }
                    }
                }

                List<ChinaArea> areas = chinaAreaDao.findByAddressParentIdOrAddressParentIdAndLogicDelete(var1,var2, Constant.LogicDelete.LOGIC_DELETE_N);
                if (CollectionUtils.isNotEmpty(areas)) {
                    List<ResponseChinaArea> data = areas
                            .stream()
                            .map(this::convertResponseChinaArea)
                            .collect(Collectors.toList());
                    returnValue = ApiResponse.buildSuccess(data);
                }
            }
        } else {
            List<ChinaArea> chinaAreas = chinaAreaDao.findByAddressTypeAndLogicDelete(1, Constant.LogicDelete.LOGIC_DELETE_N);
            if (CollectionUtils.isNotEmpty(chinaAreas)) {
                List<ResponseChinaArea> data = chinaAreas
                        .stream()
                        .filter(item -> !item.getAddressCode().equals("0"))
                        .map(this::convertResponseChinaArea)
                        .collect(Collectors.toList());
                returnValue = ApiResponse.buildSuccess(data);
            }
        }

        return returnValue;
    }

    private ResponseChinaArea convertResponseChinaArea(ChinaArea chinaArea) {
        ResponseChinaArea responseChinaArea = new ResponseChinaArea();
        BeanUtils.copyProperties(chinaArea, responseChinaArea);
        return responseChinaArea;
    }


    @Override
    public ApiResponse<Boolean> configDefaultPage(String value, String text) {
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00003, Constant.Message.DEFAULT_PAGE);

        if (StringUtils.isNotBlank(value)) {
            List<GlobalConfig> globalConfig = globalConfigDao.findAll();

            if (CollectionUtils.isNotEmpty(globalConfig)) {
                GlobalConfig config = globalConfig.get(0);
                config.setValue(value);
                config.setText(text);
                config.setModifiedDate(LocalDateTime.now());
                globalConfigDao.save(config);
                returnValue = ApiResponse.buildSuccess(true);
            }
        }

        return returnValue;
    }

    //查询初始化配置
    @Override
    public ApiResponse<ResponseUserDetails> queryInitCon() {

        ResponseUserDetails.ResponseUserDetailsBuilder builder = ResponseUserDetails.builder();

        GlobalConfig var = globalConfigDao.find();
        if (Objects.isNull(var)) {
            builder.isInitConf(0);  //未初始化
        } else {
            builder.isInitConf(1);       //已初始化
            ResponseGlobalConfig responseGlobalConfig = new ResponseGlobalConfig();
            BeanUtils.copyProperties(var, responseGlobalConfig);
            builder.globalConfig(responseGlobalConfig);
        }
        return ApiResponse.buildSuccess(builder.build());
    }
}
