package com.h3c.vdi.viewscreen.service.monitoring;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.api.model.response.monitoring.*;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.api.result.page.ApiPage;
import com.h3c.vdi.viewscreen.config.LocalDateAdapter;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.*;
import com.h3c.vdi.viewscreen.entity.*;
import com.h3c.vdi.viewscreen.utils.httpclient.HttpDefaultUtil;
import com.h3c.vdi.viewscreen.utils.redis.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lgq
 * @since 2020/6/19 16:06
 */
@Service
@Log4j2
public class MonitoringServiceImpl implements MonitoringService {


    @Resource
    TotalTimeDao totalTimeDao;

    @Resource
    private ChinaAreaDao chinaAreaDao;

    @Resource
    private CollegeDynamicDao collegeDynamicDao;

    @Resource
    private CollegeLastDynamicDao collegeLastDynamicDao;

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private OnLineInfoDao onLineInfoDao;

    @Resource
    private RedisUtil redisUtil;

    private final Gson gson = new Gson();


//    //查询全国监控数据
//    @Override
//    public ApiResponse<ResponseMonitoring> query() {
//
//        ResponseMonitoring.ResponseMonitoringBuilder builder = ResponseMonitoring.builder();
//        TotalTime totalTime = totalTimeDao.findByLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
//        if (null != totalTime) {
//            builder.totalTimeNationwide(totalTime.getTotalTimeNationwide());
//        }
//
//        ChinaArea chinaArea = chinaAreaDao.findByAddressParentIdAndAddressTypeAndLogicDelete(Constant.Code.NUM_0, Constant.Code.NUM_0, Constant.LogicDelete.LOGIC_DELETE_N);
//        builder.classSum(null == chinaArea.getClassSum() ? Constant.Code.NUM_0 : chinaArea.getClassSum())
//                .desktopSum(null == chinaArea.getDesktopSum() ? Constant.Code.NUM_0 : chinaArea.getDesktopSum())
//                .courseSum(null == chinaArea.getCourseSum() ? Constant.Code.NUM_0 : chinaArea.getCourseSum())
//                .userSum(null == chinaArea.getUserSum() ? Constant.Code.NUM_0 : chinaArea.getUserSum())
//                .schoolSum(null == chinaArea.getSchoolSum() ? Constant.Code.NUM_0 : chinaArea.getSchoolSum())
//                .type(Constant.Code.NUM_1);
//
//        List<ChinaArea> list = chinaAreaDao.queryArea();
//        if (CollectionUtils.isNotEmpty(list)) {
//            builder.areaList(list.stream().map(item -> convertResponseMonitoring(item, Constant.Code.NUM_1)).collect(Collectors.toList()));
//        }
//
//
//        //构建查询条件
////        ChinaArea area = new ChinaArea();
////        area.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
////        area.setAddressType(1);
////        Example example = Example.of(area);
////        Sort sort = Sort.by(Sort.Direction.DESC, "school_sum");
////        List list = chinaAreaDao.findAll(example, sort);
//        return ApiResponse.buildSuccess(builder.build());
//    }

    //查询全国监控数据
    @Override
    public ApiResponse<ResponseMonitoring> query() {

        ResponseMonitoring.ResponseMonitoringBuilder builder = ResponseMonitoring.builder();
        TotalTime totalTime = totalTimeDao.findByLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
        if (null != totalTime) {
            builder.totalTimeNationwide(totalTime.getTotalTimeNationwide());
        }

        Map<String, Object> areaTotalSum = shareAreaDao.queryNationwide();
        if (MapUtils.isNotEmpty(areaTotalSum)) {
            ShareArea shareArea = gson.fromJson(gson.toJson(areaTotalSum), ShareArea.class);
            builder.classSum(null == shareArea.getClassSum() ? 0 : shareArea.getClassSum())
                    .desktopSum(null == shareArea.getDesktopSum() ? 0 : shareArea.getDesktopSum())
                    .courseSum(null == shareArea.getCourseSum() ? 0 : shareArea.getCourseSum())
                    .userSum(null == shareArea.getUserSum() ? 0 : shareArea.getUserSum())
                    .schoolSum(shareAreaDao.queryNationwideSchoolSum())
                    .type(1);
        }


        List<ChinaArea> list = chinaAreaDao.queryArea();

        if (CollectionUtils.isNotEmpty(list)) {

            list.forEach(item -> {

                List<ShareArea> provinceSchoolSum = shareAreaDao.findByProvinceCodeAndLogicDelete(item.getAddressCode(), Constant.LogicDelete.LOGIC_DELETE_N);
                if (CollectionUtils.isNotEmpty(provinceSchoolSum)) {
                    item.setSchoolSum((long) provinceSchoolSum.size());
                } else {
                    item.setSchoolSum(0L);
                }

                Map<String, Object> var = shareAreaDao.findRestSum(item.getAddressCode());
                if (MapUtils.isNotEmpty(var)) {
                    ChinaArea area = gson.fromJson(gson.toJson(var), ChinaArea.class);
                    BeanUtil.copyProperties(area, item, CopyOptions.create().setIgnoreNullValue(true));
                }

            });

            List<ResponseChinaAreaDetails> data = list
                    .stream()
                    .map(item -> convertResponseMonitoring(item, 1))
                    .sorted(Comparator.comparing(ResponseChinaAreaDetails::getSchoolSum)
                            .reversed())
                    .collect(Collectors.toList());

            builder.areaList(data);
        }

        return ApiResponse.buildSuccess(builder.build());
    }

    //获取首页局点动态分页数据
    @Override
    public ApiResponse<ApiPage<ResponsePageCollegeDynamic>> queryPage(Integer current, Integer size) {
        ApiResponse<ApiPage<ResponsePageCollegeDynamic>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, Constant.Message.PAGE);

        Page<CollegeDynamic> page = collegeDynamicDao.findAll(Example.of(new CollegeDynamic().setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N)),
                PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "addDate", "id")));


        if (CollectionUtils.isNotEmpty(page.getContent())) {
            List<ResponsePageCollegeDynamic> returnList = page.get().map(this::convertResponsePageCollegeDynamic).collect(Collectors.toList());
            ApiPage<ResponsePageCollegeDynamic> returnPage = new ApiPage<>(current, page.getSize(), page.getTotalElements());
            for (ResponsePageCollegeDynamic var : returnList) {
                ShareArea data = shareAreaDao.findByUuidAndLogicDelete(var.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
                if (null != data) {
                    var.setProvinceName(data.getProvinceName());
                    var.setCityName(data.getCityName());
                    var.setCollege(data.getCollege());
                }
            }
            returnPage.setRecords(returnList);
            returnValue = ApiResponse.buildSuccess(returnPage);
        }
        return returnValue;
    }


    /**
     * 根据code获取地区监控数据
     */
//    @Override
//    public ApiResponse<ResponseMonitoring> query(String code, Integer type) {
//        ApiResponse<ResponseMonitoring> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
//
//        ChinaArea chinaArea = chinaAreaDao.findByAddressTypeAndAddressCodeAndLogicDelete(type, code, Constant.LogicDelete.LOGIC_DELETE_N);
//
//
//        //TODO 查询省市县信息
//        if (null != chinaArea) {
//
//            List<ChinaArea> chinaAreaList = new ArrayList<>();
//            List<ChinaArea> areas = null;
//            if (type == Constant.Code.NUM_1 || type == Constant.Code.NUM_2) {
//
//                if ((type == Constant.Code.NUM_1) && (
//                        chinaArea.getAddressName().equals("北京") ||
//                                chinaArea.getAddressName().equals("天津") ||
//                                chinaArea.getAddressName().equals("上海") ||
//                                chinaArea.getAddressName().equals("重庆") ||
//                                chinaArea.getAddressName().equals("香港") ||
//                                chinaArea.getAddressName().equals("澳门")
//                )) {
//
//                    areas = chinaAreaDao.findByAddressParentIdAndLogicDelete(chinaArea.getId(), Constant.LogicDelete.LOGIC_DELETE_N);
//                    if (CollectionUtils.isNotEmpty(areas)) {
//                        chinaAreaList = chinaAreaDao.findByAddressParentIdAndLogicDelete(areas.get(0).getId(), Constant.LogicDelete.LOGIC_DELETE_N);
//                        type = Constant.Code.NUM_2;
//                        code = areas.get(0).getAddressCode();
//                    }
//                } else {
//                    chinaAreaList = chinaAreaDao.findByAddressParentIdAndLogicDelete(chinaArea.getId(), Constant.LogicDelete.LOGIC_DELETE_N);
//                }
//            }
//
//
//            ResponseMonitoring.ResponseMonitoringBuilder builder = ResponseMonitoring.builder();
//
//
//            builder.schoolSum(chinaArea.getSchoolSum() == null ? Constant.Code.NUM_0 : chinaArea.getSchoolSum())
//                    .hostSum(chinaArea.getHostSum() == null ? Constant.Code.NUM_0 : chinaArea.getHostSum())
//                    .terminalSum(chinaArea.getTerminalSum() == null ? Constant.Code.NUM_0 : chinaArea.getTerminalSum())
//                    .classSum(chinaArea.getClassSum() == null ? Constant.Code.NUM_0 : chinaArea.getClassSum());
//            if (Objects.isNull(areas)) {
//                if (type == Constant.Code.NUM_1) builder.type(Constant.Code.NUM_2);
//                if (type == Constant.Code.NUM_2) builder.type(Constant.Code.NUM_3);
//            } else {
//                builder.type(Constant.Code.NUM_3);
//            }
//
//            if (type == Constant.Code.NUM_1) queryProvince(code, builder);
//            if (type == Constant.Code.NUM_2) queryCity(code, builder);
//            if (type == Constant.Code.NUM_3) {
//                queryCounty(code, builder);
//                chinaAreaList.add(chinaArea);
//            }
//
//            builderList(builder, chinaAreaList, null, null, null, null, null);
//
//            returnValue = ApiResponse.buildSuccess(builder.build());
//        }
//
//        return returnValue;
//    }


    /**
     * 根据code获取地区监控数据
     */
    @Override
    public ApiResponse<ResponseMonitoring> query(String code, Integer type) {
        ApiResponse<ResponseMonitoring> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        ResponseMonitoring.ResponseMonitoringBuilder builder = ResponseMonitoring.builder();

        try {
            if (type == 1) {
                getProvinceResult(code, type, builder);
                return ApiResponse.buildSuccess(builder.build());
            }

            if (type == 2) {
                getCityResult(code, "-1", type, builder);
                return ApiResponse.buildSuccess(builder.build());
            }

            if (type == 3) {
                getCountyResult(code, builder);
                return ApiResponse.buildSuccess(builder.build());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return returnValue;
    }

    private void getProvinceResult(String code, Integer type, ResponseMonitoring.ResponseMonitoringBuilder builder) {


        ChinaArea special = chinaAreaDao.findByAddressTypeAndAddressCodeAndLogicDelete(type, code, Constant.LogicDelete.LOGIC_DELETE_N);
        if (special.getAddressCode().equals("110000") ||
                special.getAddressCode().equals("120000") ||
                special.getAddressCode().equals("310000") ||
                special.getAddressCode().equals("500000") ||
                special.getAddressCode().equals("810000") ||
                special.getAddressCode().equals("820000")) {

            String var1;
            String var2 = "-1";


            List<ChinaArea> chinaAreaList = chinaAreaDao.findByAddressParentIdAndLogicDelete(special.getId(), Constant.LogicDelete.LOGIC_DELETE_N);
            if (CollectionUtils.isNotEmpty(chinaAreaList)) {
                var1 = chinaAreaList.get(0).getAddressCode();
                if (chinaAreaList.size() > 1) {
                    var2 = chinaAreaList.get(1).getAddressCode();
                }

                getCityResult(var1, var2, 2, builder);
            }
        } else {
            //查询当前code概要信息
            Map<String, Object> map = shareAreaDao.findProvinceSum(code);
            if (MapUtils.isNotEmpty(map)) {
                ShareArea shareArea = gson.fromJson(gson.toJson(map), ShareArea.class);
                builder.schoolSum(shareAreaDao.findProvinceSchoolSum(code))
                        .hostSum(shareArea.getHostSum())
                        .terminalSum(shareArea.getTerminalSum())
                        .classSum(shareArea.getClassSum());
            }

            //查询区域信息
            ChinaArea chinaArea = chinaAreaDao.findByAddressTypeAndAddressCodeAndLogicDelete(type, code, Constant.LogicDelete.LOGIC_DELETE_N);
            if (Objects.nonNull(chinaArea)) {


                List<ChinaArea> chinaAreaList = chinaAreaDao.findByAddressParentIdAndLogicDelete(chinaArea.getId(), Constant.LogicDelete.LOGIC_DELETE_N);

                chinaAreaList.forEach(item -> {

                    Map<String, Object> var = shareAreaDao.findCityAllSum(chinaArea.getAddressCode(), item.getAddressCode());
                    if (MapUtils.isNotEmpty(var)) {
                        ChinaArea data = gson.fromJson(gson.toJson(var), ChinaArea.class);
                        BeanUtil.copyProperties(data, item, CopyOptions.create().setIgnoreNullValue(true));
                    }
                });


                //查询最新动态
                List<CollegeLastDynamic> collegeLastDynamicList = collegeLastDynamicDao.queryProvinceLastDynamic(code);


                //查询上课次数，上课时长
                List<Map<String, Object>> attendClassSumList = shareAreaDao.queryProvinceAttendClassSum(code);

                List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryProvinceAttendClassTime(code);

                //查询在线信息
                List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryProvinceInfo(code);


                //查询局点信息
                List<ShareArea> shareAreaList = shareAreaDao.findByProvinceCodeAndLogicDelete(code, Constant.LogicDelete.LOGIC_DELETE_N);

                builderList(builder, chinaAreaList, shareAreaList, collegeLastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
                builder.type(2);
            }
        }
    }


    private void getCityResult(String code1, String code2, Integer type, ResponseMonitoring.ResponseMonitoringBuilder builder) {


        //查询当前code概要信息
        Map<String, Object> map = shareAreaDao.findCitySum(code1, code2);
        if (MapUtils.isNotEmpty(map)) {
            ShareArea shareArea = gson.fromJson(gson.toJson(map), ShareArea.class);
            builder.schoolSum(shareAreaDao.findCitySchoolSum(code1, code2))
                    .hostSum(shareArea.getHostSum())
                    .terminalSum(shareArea.getTerminalSum())
                    .classSum(shareArea.getClassSum());
        }

        //查询区域信息
        List<ChinaArea> dataList = chinaAreaDao.findByAddressTypeAndAddressCodeOrAddressCodeAndLogicDelete(type, code1, code2, Constant.LogicDelete.LOGIC_DELETE_N);
        if (CollectionUtils.isNotEmpty(dataList)) {
            if (dataList.size() == 1) {
                ChinaArea area = dataList.get(0);
                List<ChinaArea> list = chinaAreaDao.findByAddressParentIdAndLogicDelete(area.getId(), Constant.LogicDelete.LOGIC_DELETE_N);

                list.forEach(item -> {

                    Map<String, Object> var = shareAreaDao.findCountyAllSum(area.getAddressCode(), item.getAddressCode());
                    if (MapUtils.isNotEmpty(var)) {
                        ChinaArea data = gson.fromJson(gson.toJson(var), ChinaArea.class);
                        BeanUtil.copyProperties(data, item, CopyOptions.create().setIgnoreNullValue(true));
                    }
                });


                //查询最新动态
                List<CollegeLastDynamic> collegeLastDynamicList = collegeLastDynamicDao.queryCityLastDynamic(code1);

                //查询上课次数，上课时长
                List<Map<String, Object>> attendClassSumList = shareAreaDao.queryCityAttendClassSum(code1);

                List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryCityAttendClassTime(code1);

                //查询在线信息
                List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryCityInfo(code1);

                //查询局点信息
                List<ShareArea> shareAreaList = shareAreaDao.findByCityCodeAndLogicDelete(code1, Constant.LogicDelete.LOGIC_DELETE_N);

                builderList(builder, list, shareAreaList, collegeLastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
                builder.type(3);
            } else {

                ChinaArea area1 = dataList.get(0);
                ChinaArea area2 = dataList.get(1);


                List<ChinaArea> list = chinaAreaDao.findByAddressParentIdOrAddressParentIdAndLogicDelete(area1.getId(), area2.getId(), Constant.LogicDelete.LOGIC_DELETE_N);

                list.forEach(item -> {

                    Map<String, Object> var = shareAreaDao.findCountyAllSum(area1.getAddressCode(), area2.getAddressCode(), item.getAddressCode());
                    if (MapUtils.isNotEmpty(var)) {
                        ChinaArea data = gson.fromJson(gson.toJson(var), ChinaArea.class);
                        BeanUtil.copyProperties(data, item, CopyOptions.create().setIgnoreNullValue(true));
                    }
                });


                //查询最新动态
                List<CollegeLastDynamic> collegeLastDynamicList = collegeLastDynamicDao.queryCityLastDynamic(code1, code2);

                //查询上课次数，上课时长
                List<Map<String, Object>> attendClassSumList = shareAreaDao.queryCityAttendClassSum(code1, code2);

                List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryCityAttendClassTime(code1, code2);

                //查询在线信息
                List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryCityInfo(code1, code2);

                //查询局点信息
                List<ShareArea> shareAreaList = shareAreaDao.findByCityCodeOrCityCodeAndLogicDelete(code1, code2, Constant.LogicDelete.LOGIC_DELETE_N);

                builderList(builder, list, shareAreaList, collegeLastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
                builder.type(3);
            }
        }

    }


    private void getCountyResult(String code, ResponseMonitoring.ResponseMonitoringBuilder builder) {

        //查询当前code概要信息
        Map<String, Object> map = shareAreaDao.findCountySum(code);
        if (MapUtils.isNotEmpty(map)) {
            ShareArea shareArea = gson.fromJson(gson.toJson(map), ShareArea.class);
            builder.schoolSum(shareAreaDao.findCountySchoolSum(code))
                    .hostSum(shareArea.getHostSum())
                    .terminalSum(shareArea.getTerminalSum())
                    .classSum(shareArea.getClassSum());
        }


        //查询最新动态
        List<CollegeLastDynamic> collegeLastDynamicList = collegeLastDynamicDao.queryCountyLastDynamic(code);

        //查询上课次数，上课时长
        List<Map<String, Object>> attendClassSumList = shareAreaDao.queryCountyAttendClassSum(code);

        List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryCountyAttendClassTime(code);

        //查询在线信息
        List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryCountyInfo(code);

        //查询局点信息
        List<ShareArea> shareAreaList = shareAreaDao.findByCountyCodeAndLogicDelete(code, Constant.LogicDelete.LOGIC_DELETE_N);

        builderList(builder, null, shareAreaList, collegeLastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
    }


    //todo 省
    private void queryProvince(String code, ResponseMonitoring.ResponseMonitoringBuilder builder) {

        List<ShareArea> shareAreaList = shareAreaDao.findByProvinceCodeAndLogicDelete(code, Constant.LogicDelete.LOGIC_DELETE_N);
        List<Map<String, Object>> attendClassSumList = shareAreaDao.queryProvinceAttendClassSum(code);
        List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryProvinceAttendClassTime(code);
        List<CollegeLastDynamic> lastDynamicList = collegeLastDynamicDao.queryProvinceLastDynamic(code);
        List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryProvinceInfo(code);

        builderList(builder, null, shareAreaList, lastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
    }

    //todo 市
    private void queryCity(String code, ResponseMonitoring.ResponseMonitoringBuilder builder) {

        List<ShareArea> shareAreaList = shareAreaDao.findByCityCodeAndLogicDelete(code, Constant.LogicDelete.LOGIC_DELETE_N);
        List<Map<String, Object>> attendClassSumList = shareAreaDao.queryCityAttendClassSum(code);
        List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryCityAttendClassTime(code);
        List<CollegeLastDynamic> lastDynamicList = collegeLastDynamicDao.queryCityLastDynamic(code);
        List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryCityInfo(code);
        builderList(builder, null, shareAreaList, lastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
    }

    //todo 县
    private void queryCounty(String code, ResponseMonitoring.ResponseMonitoringBuilder builder) {

        List<ShareArea> shareAreaList = shareAreaDao.findByCountyCodeAndLogicDelete(code, Constant.LogicDelete.LOGIC_DELETE_N);
        List<Map<String, Object>> attendClassSumList = shareAreaDao.queryCountyAttendClassSum(code);
        List<Map<String, Object>> attendClassTimeList = shareAreaDao.queryCountyAttendClassTime(code);
        List<CollegeLastDynamic> lastDynamicList = collegeLastDynamicDao.queryCountyLastDynamic(code);
        List<Map<String, Object>> onLineInfoList = onLineInfoDao.queryCountyInfo(code);
        builderList(builder, null, shareAreaList, lastDynamicList, attendClassSumList, attendClassTimeList, onLineInfoList);
    }


    private void builderList(ResponseMonitoring.ResponseMonitoringBuilder builder,
                             List<ChinaArea> chinaAreaList,
                             List<ShareArea> shareAreaList,
                             List<CollegeLastDynamic> lastDynamicList,
                             List<Map<String, Object>> attendClassSumList,
                             List<Map<String, Object>> attendClassTimeList,
                             List<Map<String, Object>> onLineInfoList) {
        Gson var = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        if (null != checkList(chinaAreaList))
            builder.areaList(chinaAreaList
                    .stream()
                    .map(item -> convertResponseMonitoring(item, null))
                    .collect(Collectors.toList()));

        if (null != checkList(shareAreaList))
            builder.shareAreaList(shareAreaList
                    .stream()
                    .map(this::convertResponseShareArea)
                    .collect(Collectors.toList()));


        if (null != checkList(lastDynamicList))
            builder.lastDynamicList(lastDynamicList
                    .stream()
                    .map(this::convertResponseLastDynamic)
                    .collect(Collectors.toList()));


        if (null != checkList(attendClassSumList))
            builder.attendClassSumList(attendClassSumList
                    .stream()
                    .map(item -> var.fromJson(var.toJson(item), ResponseCourseRankings.class)
                            .setAttendClassTime(null))
                    .collect(Collectors.toList()));


        if (null != checkList(attendClassTimeList))
            builder.attendClassTimeList(attendClassTimeList
                    .stream()
                    .map(item -> var.fromJson(var.toJson(item), ResponseCourseRankings.class)
                            .setAttendClassSum(null))
                    .collect(Collectors.toList()));


        if (null != checkList(onLineInfoList))
            builder.onLineInfoList(onLineInfoList
                    .stream()
                    .map(item -> var.fromJson(var.toJson(item), ResponseOnLineInfo.class))
                    .collect(Collectors.toList()));
    }


    private ResponseShareArea convertResponseShareArea(ShareArea shareArea) {
        ResponseShareArea responseShareArea = new ResponseShareArea();
        BeanUtils.copyProperties(shareArea, responseShareArea);
        String str = (responseShareArea.getProvinceName() == null ? "" : responseShareArea.getProvinceName()) +
                (responseShareArea.getCityName() == null ? "" : responseShareArea.getCityName()) +
                (responseShareArea.getCountyName() == null ? "" : responseShareArea.getCountyName()) +
                (responseShareArea.getCollege() == null ? "" : responseShareArea.getCollege());
        log.info("============================================================================={areaDetailsLocation：}" + str);

        // todo 经纬度去缓存拿，先看缓存是否命中，如果没有调用高德地图，拿到经纬度存入缓存
        String location = (String) redisUtil.hget("uuid", str+responseShareArea.getUuid());
        if (StringUtils.isBlank(location)) {
            location = HttpDefaultUtil.getLocation(str);
            if (null != location) {
                //todo 因为位置信息可能会发生变化，所以此key不建议持久化，应该设置过期时间，现阶段暂未设置过期时间
                redisUtil.hset("uuid", str+responseShareArea.getUuid(), location);
            }
        }
        responseShareArea.setLocation(location);
        return responseShareArea;
    }


    private ResponsePageCollegeDynamic convertResponsePageCollegeDynamic(CollegeDynamic item) {
        ResponsePageCollegeDynamic returnValue = new ResponsePageCollegeDynamic();
        BeanUtils.copyProperties(item, returnValue);
        return returnValue;
    }


//    private ResponseChinaAreaDetails convertResponseMonitoring(ChinaArea item, Integer type) {
//        ResponseChinaAreaDetails returnValue = new ResponseChinaAreaDetails();
//        BeanUtils.copyProperties(item, returnValue);
//
//        if (type != null && type == Constant.Code.NUM_1) {        //设置省经纬度
//            StringBuilder stringBuilder = new StringBuilder();
//            Map<String, Object> map = new HashMap<>();
//            String addressName = returnValue.getAddressName();
////            String location = HttpUtil.getLocation(addressName);
////            if (null != location) {
////                returnValue.setLocation(location);
////            }
//            //设置市经纬度
//            List<ChinaArea> areaList = chinaAreaDao.findByAddressParentIdAndLogicDeleteAndModifiedDateIsNotNull(item.getId(), Constant.LogicDelete.LOGIC_DELETE_N);
//            if (CollectionUtils.isNotEmpty(areaList)) {
//                for (ChinaArea chinaArea : areaList) {
//                    stringBuilder.append(addressName).append(chinaArea.getAddressName());
//                    log.info("============================================================================={cityLocation：}" + stringBuilder.toString());
//                    String location = HttpProxyUtil.getLocation(stringBuilder.toString());
//                    if (null != location) {
//                        map.put(chinaArea.getAddressCode(), location);
//                    }
//                }
//            }
//            returnValue.setLocation(map);
//        }
//
//        return returnValue;
//    }


    private ResponseChinaAreaDetails convertResponseMonitoring(ChinaArea item, Integer type) {
        ResponseChinaAreaDetails returnValue = new ResponseChinaAreaDetails();
        BeanUtils.copyProperties(item, returnValue);

        if (type != null && type == 1) {        //设置省经纬度
            Map<String, Object> map = new HashMap<>();

            //设置省份经纬度
//            List<ShareArea> list = shareAreaDao.findProvinceAndCity(returnValue.getAddressCode());
            List<ShareArea> list = shareAreaDao.findByProvinceCodeAndLogicDelete(returnValue.getAddressCode(), Constant.LogicDelete.LOGIC_DELETE_N);

            if (CollectionUtils.isNotEmpty(list)) {

                ShareArea var = list.get(0);
                // todo 经纬度去缓存拿，先看缓存是否命中，如果没有调用高德地图，拿到经纬度存入缓存
                String adCode = var.getProvinceCode();
                String location = (String) redisUtil.hget("province", adCode);
                if (StringUtils.isBlank(location)) {
                    location = HttpDefaultUtil.getLocation(var.getProvinceName());
                    if (location != null) {
                        //todo 一共34个省份adCode，所以无需设置过期时间，持久化即可
                        redisUtil.hset("province", adCode, location);
                    }
//                    else {
//                        adCode = "";
//                    }
                }
                map.put(adCode, location);
            }

            returnValue.setLocation(map);
        }

        return returnValue;
    }


    private ResponseLastDynamic convertResponseLastDynamic(CollegeLastDynamic item) {
        ResponseLastDynamic returnValue = new ResponseLastDynamic();
        BeanUtils.copyProperties(item, returnValue);
        if (null != returnValue.getClassroomStatus()) {
            if (returnValue.getClassroomStatus() == 0)
                returnValue.setName(Constant.Message.ATTEND_CLASS);
            if (returnValue.getClassroomStatus() == 1)
                returnValue.setName(Constant.Message.FINISH_CLASS);
        }
        if (null != returnValue.getClassBeginTime()) returnValue.setTime(returnValue.getClassBeginTime());
        if (null != returnValue.getClassOverTime()) returnValue.setTime(returnValue.getClassOverTime());
        returnValue.setCollege(shareAreaDao.findCollegeName(returnValue.getUuid()));
        return returnValue;
    }


    private <R> List<R> checkList(List<R> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        return null;
    }


}
