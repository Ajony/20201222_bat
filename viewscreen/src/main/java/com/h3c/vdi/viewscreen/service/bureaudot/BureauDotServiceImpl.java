package com.h3c.vdi.viewscreen.service.bureaudot;

import com.h3c.vdi.viewscreen.api.model.response.bureaudot.ResponsePageBureauDot;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.api.result.page.ApiPage;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.*;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.entity.TotalTime;
import com.h3c.vdi.viewscreen.utils.common.QueryUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Date 2020/11/3 14:07
 * @Created by lgw2845
 */
@Service
@Log4j2
public class BureauDotServiceImpl implements BureauDotService {

    @Resource
    ShareAreaDao shareAreaDao;

    @Resource
    CollegeInfoDao collegeInfoDao;

    @Resource
    ClassroomDao classroomDao;

    @Resource
    CourseDao courseDao;

    @Resource
    HostDao hostDao;

    @Resource
    VmDao vmDao;

    @Resource
    CollegeLastDynamicDao collegeLastDynamicDao;

    @Resource
    CollegeDynamicDao collegeDynamicDao;

    @Resource
    StorageColonyInfoDao storageColonyInfoDao;

    @Resource
    OnLineInfoDao onLineInfoDao;

    @Resource
    TerminalHistoryDao terminalHistoryDao;

    @Resource
    TotalTimeDao totalTimeDao;

    @Resource
    SoftwareDao softwareDao;

    //todo 版本数据需求待定


    @Override
    public ApiResponse<ApiPage<ResponsePageBureauDot>> page(Integer current, Integer size, String provinceName, String cityName, String countyName, String college) {

        ApiResponse<ApiPage<ResponsePageBureauDot>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, Constant.Message.PAGE);

        //构建条件查询函数
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("provinceName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cityName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("countyName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("college", ExampleMatcher.GenericPropertyMatchers.contains());


        ShareArea var = new ShareArea();
        var.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
        var.setProvinceName(provinceName);
        var.setCityName(cityName);
        var.setCountyName(countyName);
        var.setCollege(college);


        Page<ShareArea> page = shareAreaDao.findAll(Example.of(var, matcher),
                PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "modifiedDate")));

        return getApiPageApiResponse(current, returnValue, page);
    }


    @Override
    public ApiResponse<ApiPage<ResponsePageBureauDot>> homoPage(Integer current, Integer size) {

        ApiResponse<ApiPage<ResponsePageBureauDot>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, Constant.Message.PAGE);

        Page<ShareArea> page = shareAreaDao.findAll(Example.of(new ShareArea().setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N)),
                PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "onlineDate")));

        return getApiPageApiResponse(current, returnValue, page);
    }


    private ApiResponse<ApiPage<ResponsePageBureauDot>> getApiPageApiResponse(Integer current, ApiResponse<ApiPage<ResponsePageBureauDot>> returnValue, Page<ShareArea> page) {
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            List<ResponsePageBureauDot> returnList = page.get()
                    .map(this::convertResponseBureauDot)
//                    .sorted(Comparator.comparing(ResponsePageBureauDot::getModifiedDate).reversed())
//                    .sorted(Comparator.comparing(ResponsePageBureauDot::getOnlineDate)
//                    .thenComparing(ResponsePageBureauDot::getModifiedDate).reversed())
                    .collect(Collectors.toList());
            ApiPage<ResponsePageBureauDot> returnPage = new ApiPage<>(current, page.getSize(), page.getTotalElements());
            returnPage.setRecords(returnList);
            returnValue = ApiResponse.buildSuccess(returnPage);
        }
        return returnValue;
    }

/*    @Override
    public void delete(String id) {

        if (StringUtils.isBlank(id)) {
            return ApiResponse.buildError(ApiErrorEnum.E50101, id);
        }

        ShareArea var = shareAreaDao.findByIdAndLogicDelete(id, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(var)) {
            return ApiResponse.buildError(ApiErrorEnum.E00004, id);
        }

        if (Objects.isNull(var.getReportd())) {
            return ApiResponse.buildError(ApiErrorEnum.S00001, id);
        }

        if (var.getReportd().equals("yes")) {
            return ApiResponse.buildError("500", "该局点处于上报状态，请关闭上报状态后重新删除。");
        }

        //开始清除局点历史数据
        log.info("CLEAR HISTORY DATA BEGIN : SUCCESS -----------------------------> {}", var.getUuid());
        clearBureauDotHistoryData(uuid, collegeDynamicDao, collegeInfoDao,
                classroomDao, courseDao, hostDao,
                vmDao, collegeLastDynamicDao, storageColonyInfoDao,
                onLineInfoDao, terminalHistoryDao, shareAreaDao);
        log.info("CLEAR HISTORY DATA END : SUCCESS -----------------------------> {}", var.getUuid());

        return ApiResponse.buildSuccess(true);
    }*/


    //批量删除
    @Override
    public ApiResponse<Boolean> deleteByIds(String ids) {

        ApiResponse<Boolean> returnValue = ApiResponse.buildError(false, ApiErrorEnum.E00004, "");

        if (StringUtils.isBlank(ids)) {
            return ApiResponse.buildError(ApiErrorEnum.E50101, "ids");
        }

        //判断是删除单个还是多个
        if (!ids.contains(",")) {

            ShareArea var = shareAreaDao.findByIdAndLogicDelete(Long.valueOf(ids), Constant.LogicDelete.LOGIC_DELETE_N);
            if (Objects.nonNull(var)) {
                if ((StringUtils.isBlank(var.getStatus()) || var.getStatus().equals("open"))) {

                    //判断最后更新日期是否大于一天
                    LocalDateTime modifiedDate = var.getModifiedDate();
                    if (Objects.isNull(modifiedDate)) {
                        modifiedDate = var.getAddDate();
                    }

                    //更新或添加日期大于一天
                    LocalDateTime plusDays = modifiedDate.plusDays(1);
                    if (LocalDateTime.now().isBefore(plusDays)) {
                        return ApiResponse.buildError("500", Constant.Message.ERROR_MES_1);
                    }
                }
                log.info("删除单个局点信息 BEGIN: SUCCESS {}", ids);
                delete(var.getUuid());
                log.info("删除单个局点信息 END: SUCCESS {}", ids);
                returnValue = ApiResponse.buildSuccess(true);
            }
        } else {
            //删除多个
            List<Long> idListAsLong = QueryUtils.getIdListAsLong(ids);
            //确保所有局点都处于未上报状态
            for (Long id : idListAsLong) {
                ShareArea var = shareAreaDao.findByIdAndLogicDelete(id, Constant.LogicDelete.LOGIC_DELETE_N);
                if (Objects.nonNull(var)) {
                    if (StringUtils.isBlank(var.getStatus()) || var.getStatus().equals("open")) {
                        //判断最后更新日期是否大于一天
                        LocalDateTime modifiedDate = var.getModifiedDate();
                        if (Objects.isNull(modifiedDate)) {
                            modifiedDate = var.getAddDate();
                        }
                        //更新或添加日期大于一天
                        LocalDateTime plusDays = modifiedDate.plusDays(1);
                        if (LocalDateTime.now().isBefore(plusDays)) {
                            return ApiResponse.buildError("500", Constant.Message.ERROR_MES_2 + "：" + var.getCollege() + Constant.Message.ERROR_MES_3);
                        }
                    }
                }
            }

            //开始批量删除
            log.info("批量删除局点信息 BEGIN: SUCCESS {}", ids);
            for (Long id : idListAsLong) {
                ShareArea var = shareAreaDao.findByIdAndLogicDelete(id, Constant.LogicDelete.LOGIC_DELETE_N);
                if (Objects.nonNull(var)) {
                    delete(var.getUuid());
                }
            }
            log.info("批量删除局点信息 END: SUCCESS {}", ids);
            returnValue = ApiResponse.buildSuccess(true);
        }
        //删完以后重新计算一下上课总时长
        if (returnValue.getData()) {
            countTime();
            log.info("COUNT_TIME : SUCCESS");
        }

        return returnValue;
    }


    public void delete(String uuid) {
        clearBureauDotHistoryData(uuid, collegeInfoDao,
                classroomDao, courseDao, hostDao,
                vmDao, collegeLastDynamicDao, collegeDynamicDao, storageColonyInfoDao,
                onLineInfoDao, terminalHistoryDao, shareAreaDao, softwareDao);
    }


    private ResponsePageBureauDot convertResponseBureauDot(ShareArea item) {
        ResponsePageBureauDot returnValue = new ResponsePageBureauDot();
        BeanUtils.copyProperties(item, returnValue);
        return returnValue;
    }


    private void clearBureauDotHistoryData(String uuid, CollegeInfoDao collegeInfoDao,
                                           ClassroomDao classroomDao, CourseDao courseDao, HostDao hostDao, VmDao vmDao,
                                           CollegeLastDynamicDao collegeLastDynamicDao, CollegeDynamicDao collegeDynamicDao,
                                           StorageColonyInfoDao storageColonyInfoDao, OnLineInfoDao onLineInfoDao,
                                           TerminalHistoryDao terminalHistoryDao, ShareAreaDao shareAreaDao, SoftwareDao softwareDao) {
        shareAreaDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        classroomDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        courseDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        hostDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        vmDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        collegeLastDynamicDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        collegeDynamicDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        storageColonyInfoDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        onLineInfoDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        terminalHistoryDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        collegeInfoDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        softwareDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
    }


    private void countTime() {
        Long var = shareAreaDao.timeAll();
        TotalTime totalTime = totalTimeDao.findByLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);

        if (null == totalTime) {
            TotalTime time = new TotalTime();
            time.setTotalTimeNationwide(var.toString());
            time.setAddDate(LocalDateTime.now());
            time.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
            totalTimeDao.save(time);
        } else {
            totalTime.setTotalTimeNationwide(var.toString());
            totalTime.setModifiedDate(LocalDateTime.now());
            totalTimeDao.save(totalTime);
        }
    }
}
