package com.h3c.vdi.viewscreen.service;

import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dao.SubTitleDao;
import com.h3c.vdi.viewscreen.dto.SubTitleDTO;
import com.h3c.vdi.viewscreen.entity.SubTitle;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by x19765 on 2020/10/15.
 */
@Service
public class SubTitleServiceImpl implements SubTitleService {

    @Resource
    private SubTitleDao subTitleDao;

    @Override
    public ApiResponse<SubTitleDTO> getSubTitle() {
        SubTitleDTO.SubTitleDTOBuilder builder = SubTitleDTO.builder();
        List<SubTitle> entities = subTitleDao.findAll();
        if (CollectionUtils.isEmpty(entities)) {
            builder.logoTitle("").map("").siteDynamics("").siteInformation("").regionalDistribution("");
        }
        entities.forEach(entity -> {
            switch (entity.getName()) {
                case SubTitle.LOGO_TITLE:
                    builder.logoTitle(entity.getValue());
                    break;
                case SubTitle.MAP:
                    builder.map(entity.getValue());
                    break;
                case SubTitle.SITE_DYNAMICS:
                    builder.siteDynamics(entity.getValue());
                    break;
                case SubTitle.SITE_INFORMATION:
                    builder.siteInformation(entity.getValue());
                    break;
                case SubTitle.REGIONAL_DISTRIBUTION:
                    builder.regionalDistribution(entity.getValue());
                    break;
                default:
                    break;
            }
        });
        return ApiResponse.buildSuccess(builder.build());
    }

    @Override
    public ApiResponse<Boolean> editSubTitle(SubTitleDTO subTitleDTO) {
        if (null != subTitleDTO.getLogoTitle()) {
            updateByName(SubTitle.LOGO_TITLE, subTitleDTO.getLogoTitle());
        }
        if (null != subTitleDTO.getMap()) {
            updateByName(SubTitle.MAP, subTitleDTO.getMap());
        }
        if (null != subTitleDTO.getRegionalDistribution()) {
            updateByName(SubTitle.REGIONAL_DISTRIBUTION, subTitleDTO.getRegionalDistribution());
        }
        if (null != subTitleDTO.getSiteDynamics()) {
            updateByName(SubTitle.SITE_DYNAMICS, subTitleDTO.getSiteDynamics());
        }
        if (null != subTitleDTO.getSiteInformation()) {
            updateByName(SubTitle.SITE_INFORMATION, subTitleDTO.getSiteInformation());
        }
        return ApiResponse.buildSuccess(true);
    }

    @Override
    public void updateByName(String name, String value) {
        List<SubTitle> subTitles = subTitleDao.findByName(name);
        SubTitle subTitle = null;
        if (CollectionUtils.isEmpty(subTitles)) {
            subTitle = new SubTitle();
            subTitle.setName(name);
            subTitle.setValue(value);
            subTitleDao.save(subTitle);
        } else {
            subTitleDao.updateByName(value, name);
        }
    }

}
