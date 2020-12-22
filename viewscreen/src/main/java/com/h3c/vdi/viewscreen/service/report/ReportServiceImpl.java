package com.h3c.vdi.viewscreen.service.report;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestMac;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestReport;
import com.h3c.vdi.viewscreen.api.model.request.report.RequestReportShareArea;
import com.h3c.vdi.viewscreen.api.model.response.autonavi.ResponseInverse;
import com.h3c.vdi.viewscreen.api.model.response.report.ResponseReport;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.config.LocalDateAdapter;
import com.h3c.vdi.viewscreen.config.LocalDateTimeAdapter;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.*;
import com.h3c.vdi.viewscreen.entity.*;
import com.h3c.vdi.viewscreen.event.AsyncEventHandler;
import com.h3c.vdi.viewscreen.event.EventHandler;
import com.h3c.vdi.viewscreen.event.EventType;
import com.h3c.vdi.viewscreen.event.RequestData;
import com.h3c.vdi.viewscreen.exception.BusinessException;
import com.h3c.vdi.viewscreen.utils.httpclient.HttpDefaultUtil;
import com.h3c.vdi.viewscreen.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

/**
 * @author lgq
 * @since 2020/6/3 11:05
 */
@Service
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class ReportServiceImpl implements ReportService {


    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private ClassroomDao classroomDao;

    @Resource
    private CourseDao courseDao;

    @Resource
    private HostDao hostDao;

    @Resource
    private CollegeDynamicDao collegeDynamicDao;

    @Resource
    private CollegeLastDynamicDao collegeLastDynamicDao;

    @Resource
    private VmDao vmDao;

    @Resource
    private StorageColonyInfoDao storageColonyInfoDao;

    @Resource
    private VersionInfoDao versionInfoDao;

    @Resource
    private CollegeInfoDao collegeInfoDao;

    @Resource
    private SoftwareDao softwareDao;

    @Resource
    private OnLineInfoDao onLineInfoDao;

    @Resource
    private TotalTimeDao totalTimeDao;

    @Resource
    private TerminalHistoryDao terminalHistoryDao;

    @Resource
    private EventHandler eventHandler;

    @Resource
    private AsyncEventHandler asyncEventHandler;

    @Resource
    private NicListDao nicListDao;

    @Resource
    private ChinaAreaDao chinaAreaDao;

    @Resource
    private RedisUtil redisUtil;


    Gson gson = new Gson();

    private static final String DATA = "data";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String RATE = "rate";


    /**
     * 校验uuid
     */
    @Override
    public ApiResponse<ResponseReport> checkUuid(RequestMac requestMac) {

        //todo   新建mac表用来校验uuid或者新增uuid
        NicList var = nicListDao.findByUuidAndLogicDelete(requestMac.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);

        if (Objects.isNull(var)) {

            NicList data = nicListDao.findByNicListContainingAndLogicDelete(requestMac.getNicList(), Constant.LogicDelete.LOGIC_DELETE_N);
            if (Objects.nonNull(data)) {
                return ApiResponse.buildSuccess(ResponseReport.builder().uuid(data.getUuid()).status("block").build());
            } else {
                NicList entity = new NicList();
                entity.setUuid(requestMac.getUuid());
                entity.setNicList(requestMac.getNicList());
                entity.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                entity.setAddDate(LocalDateTime.now());
                nicListDao.save(entity);
            }
        } else {
            if (StringUtils.isBlank(var.getNicList())) {
                var.setNicList(requestMac.getNicList());
                var.setModifiedDate(LocalDateTime.now());
                nicListDao.save(var);
            } else {
                if (!requestMac.getNicList().equals(var.getNicList())) {
                    var.setNicList(requestMac.getNicList());
                    var.setModifiedDate(LocalDateTime.now());
                    nicListDao.save(var);
                }
            }
        }
        return ApiResponse.buildSuccess(ResponseReport.builder().status("ok").build());
    }


    @Override
    public ApiResponse<Boolean> reportSwitch(String status, String uuid) {

        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00003, "");
        try {
            shareAreaDao.reportSwitch(status, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_TIME_PATTERN)) + "_report_switch", uuid);
            returnValue = ApiResponse.buildSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return returnValue;
    }


    /**
     * 上报局点
     */
    @Override
    public ApiResponse<Boolean> reportShareArea(RequestReportShareArea requestReport) {
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.S00001, Constant.Message.ERROR_REPORT_1);

        if (Objects.nonNull(requestReport)) {

            ShareArea shareArea = shareAreaDao.findByUuidAndLogicDelete(requestReport.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
            if (Objects.nonNull(shareArea)) {

                //通过经纬度校正地点
                if (StringUtils.isNotBlank(requestReport.getLocation())) {
                    if (shareAreaDao.findByUuidAndLocationAndLogicDelete(requestReport.getUuid(), requestReport.getLocation(), Constant.LogicDelete.LOGIC_DELETE_N) == null) {
                        siteRevise(requestReport, "update");
                    } else {
                        if (!requestReport.getProvinceCode().equals(shareArea.getProvinceCode()) ||
                                !requestReport.getCityCode().equals(shareArea.getCityCode()) ||
                                !requestReport.getCountyCode().equals(shareArea.getCountyCode())) {
                            requestReport.setProvinceCode(shareArea.getProvinceCode());
                            requestReport.setProvinceName(shareArea.getProvinceName());
                            requestReport.setCityCode(shareArea.getCityCode());
                            requestReport.setCityName(shareArea.getCityName());
                            requestReport.setCountyCode(shareArea.getCountyCode());
                            requestReport.setCountyName(shareArea.getCountyName());
                        }
                    }
                }

                log.info("修改局点：ERROR -----------------------------> {}", requestReport);
                if (Objects.nonNull(shareArea.getReserver1()) && shareArea.getReserver1().equals(Constant.Message.IP_CONFIG)) {
                    requestReport.setIp(shareArea.getIp());
                }
                BeanUtils.copyProperties(requestReport, shareArea);
                shareArea.setModifiedDate(LocalDateTime.now());
                shareAreaDao.save(shareArea);
                asyncEventHandler.publish(EventType.SITE_SYNC, new RequestData<>(shareArea));
            } else {

                //通过经纬度校正地点
                if (StringUtils.isNotBlank(requestReport.getLocation())) {
                    siteRevise(requestReport, "save");
                }

                log.info("新增局点：ERROR -----------------------------> {}", requestReport);
                ShareArea data = new ShareArea();
                BeanUtils.copyProperties(requestReport, data);
                data.setAddDate(LocalDateTime.now());
                data.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                data.setOnlineDate(LocalDate.now());
                shareAreaDao.save(data);
            }
            returnValue = ApiResponse.buildSuccess(true);
        }
        return returnValue;
    }

    //todo 通过经纬度校正地点
    private void siteRevise(RequestReportShareArea requestReport, String type) {
        log.info("调用高德通过经纬度校正地理位置 BEGIN：SUCCESS {}", requestReport + type);
        ApiResponse<ResponseInverse> apiResponse = HttpDefaultUtil.locationToAddress(requestReport.getLocation());

        //todo adCode一样return即可，暂未优化

        Optional.ofNullable(apiResponse)
                .map(ApiResponse::getData)
                .map(ResponseInverse::getRegeocode)
                .map(ResponseInverse.RegeocodeBean::getAddressComponent)
                .map(ResponseInverse.RegeocodeBean.AddressComponentBean::getAdcode)
                .flatMap(item ->
                        Optional.ofNullable
                                (chinaAreaDao.findByAddressCodeAndAddressTypeAndLogicDelete(item, 3, Constant.LogicDelete.LOGIC_DELETE_N)))
                .ifPresent(a -> chinaAreaDao.findById(a.getAddressParentId())
                        .ifPresent(b -> chinaAreaDao.findById(b.getAddressParentId())
                                .ifPresent(c -> {
                                    requestReport.setProvinceCode(c.getAddressCode());
                                    requestReport.setProvinceName(c.getAddressName());
                                    requestReport.setCityCode(b.getAddressCode());
                                    requestReport.setCityName(b.getAddressName());
                                    requestReport.setCountyCode(a.getAddressCode());
                                    requestReport.setCountyName(a.getAddressName());
                                    log.info("经纬度校正地理位置：SUCCESS {}", requestReport);
                                })));
    }


    /**
     * 上报数据
     */
    @Override
    public ApiResponse<ResponseReport> reportData(List<RequestReport> requestReport, String uuid) {

        ApiResponse<ResponseReport> returnValue = ApiResponse.buildError(ApiErrorEnum.S00001, uuid + Constant.Message.ERROR_REPORT_3);

        ShareArea shareArea = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.nonNull(shareArea)) {
            try {
                log.info("上报数据：WARNING BEGIN -----------------------------> {}", uuid);
                startReportData(requestReport, uuid);
                returnValue = ApiResponse.buildSuccess(ResponseReport.builder().uuid(uuid).modifiedDate(LocalDateTime.now()).build());
                log.info("上报数据：WARNING END -----------------------------> {}", uuid);
            } catch (Exception e) {
                throw new BusinessException(Constant.Message.ERROR_REPORT_2, e.getMessage());
            }
        } else {
            log.info("上报数据：ERROR 该局点未上报 {}", uuid);
        }
        return returnValue;
    }

    private void startReportData(List<RequestReport> requestReport, String uuid) {
        for (RequestReport report : requestReport) {
            if (CollectionUtils.isNotEmpty(report.getDataList())) {
                log.info("TYPE：" + report.getType() + " -----------------------------> {}", report.getDataList().toString());
                switch (report.getType()) {
                    case Constant.ReportType.REPORT_COLLEGE_INFO:
                        List<CollegeInfo> list = saveCollegeInfo(report.getDataList(), uuid);
                        asyncEventHandler.publish(EventType.DATA_SYNC, new RequestData<>(list));
                        break;
                    case Constant.ReportType.REPORT_CLASSROOM:
                        saveClassroom(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_COURSE:
                        saveCourse(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_HOST:
                        saveHost(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_VM:
                        saveVm(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_COLLEGE_DYNAMIC:
                        saveCollegeDynamic(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_COLLEGE_LAST_DYNAMIC:
                        saveCollegeLastDynamic(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_STORAGE_COLONY_INFO:
                        saveStorageColonyInfo(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_VERSION_INFO:
                        saveVersionInfo(report.getDataList(), uuid);
                        break;
                    case Constant.ReportType.REPORT_SOFTWARE:
                        saveSoftware(report.getDataList(), uuid);
                        break;
                }
            }
        }
    }


    private List<CollegeInfo> saveCollegeInfo(List<LinkedHashMap> data, String uuid) {

        String key = "1_" + uuid;
        String value = "lock_" + uuid;
        List<CollegeInfo> dataSyncList = new ArrayList<>();

        if (redisUtil.setIfPresent(key, value, 300)) {
            log.info("上锁...............SUCCESS: {}", key);
            List<CollegeInfo> collect = data.stream().map(item -> gson.fromJson(gson.toJson(item), CollegeInfo.class)).collect(Collectors.toList());

            for (CollegeInfo collegeInfo : collect) {
                terminalHistoryOpera(collegeInfo, uuid);    //todo 记录终端每日 终端总数，终端在线数，终端使用时长

                CollegeInfo info = collegeInfoDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
                if (Objects.nonNull(info)) {
                    BeanUtil.copyProperties(collegeInfo, info, CopyOptions.create().setIgnoreNullValue(true));
                    info.setModifiedDate(LocalDateTime.now());
                    collegeInfoDao.save(info);
                    dataSyncList.add(info);
                } else {
                    collegeInfo.setUuid(uuid);
                    collegeInfo.setAddDate(LocalDateTime.now());
                    collegeInfo.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                    collegeInfoDao.save(collegeInfo);
                    dataSyncList.add(collegeInfo);
                }
            }
            redisUtil.del(key);
            log.info("释放锁...............SUCCESS: {}", key);
//            String str = (String) redisUtil.get(key);
//            if (str.equals(value)){
//                redisUtil.del(key);
//                log.info("释放锁...............SUCCESS: {}", key);
//            }
        } else {
            log.info("锁资源占用中...............SUCCESS: {}", key);
        }
        return dataSyncList;
    }

    private void terminalHistoryOpera(CollegeInfo collegeInfo, String uuid) {

        TerminalHistory var = terminalHistoryDao.queryHistory(uuid, LocalDate.now().toString());
        if (Objects.isNull(var)) {
            TerminalHistory save = new TerminalHistory();
            save.setUuid(uuid);
            save.setTerminalTotal(collegeInfo.getTerminalTotal() == null ? 0 : collegeInfo.getTerminalTotal());
            save.setTerminalOnlineSum(collegeInfo.getTerminalOnlineSum() == null ? 0 : collegeInfo.getTerminalOnlineSum());
            save.setTerminalUseTime(collegeInfo.getTerminalUsetime() == null ? 0 : collegeInfo.getTerminalUsetime());
            save.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
            save.setAddDate(LocalDateTime.now());
            terminalHistoryDao.save(save);
        } else {
            var.setTerminalTotal(collegeInfo.getTerminalTotal() == null ? 0 : collegeInfo.getTerminalTotal());
            var.setTerminalOnlineSum(collegeInfo.getTerminalOnlineSum() == null ? 0 : collegeInfo.getTerminalOnlineSum());
            var.setTerminalUseTime(collegeInfo.getTerminalUsetime() == null ? 0 : collegeInfo.getTerminalUsetime());
            var.setModifiedDate(LocalDateTime.now());
            terminalHistoryDao.save(var);
        }
    }


    private void saveStorageColonyInfo(List<LinkedHashMap> data, String uuid) {
        storageColonyInfoDao.saveAll(data
                .stream()
                .map(item -> {
                    Gson gson = new Gson()
                            .newBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                            .create();
                    return gson
                            .fromJson(gson.toJson(item), StorageColonyInfo.class)
                            .setUuid(uuid)
                            .setAddDate(LocalDateTime.now())
                            .setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                })
                .collect(Collectors.toList()));
    }

    //todo 新增或修改当天数据
    private void saveClassroom(List<LinkedHashMap> data, String uuid) {

        List<Classroom> collect = data.stream().map(item -> {
            Gson gson = new Gson()
                    .newBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            return gson.fromJson(gson.toJson(item), Classroom.class);
        }).collect(Collectors.toList());


        for (Classroom classroom : collect) {
            Classroom room = classroomDao.queryClassroom(uuid, classroom.getDate().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_YEAR_MONTH)));
            if (Objects.isNull(room)) {
                classroom.setUuid(uuid);
                classroom.setAddDate(LocalDateTime.now());
                classroom.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                classroomDao.save(classroom);
            } else {
                BeanUtil.copyProperties(classroom, room, CopyOptions.create().setIgnoreNullValue(true));
                room.setModifiedDate(LocalDateTime.now());
                classroomDao.save(room);
            }
        }
    }


    //TODO  新增之前拿出上课时长和次数进行累加然后删除
    private void saveCourse(List<LinkedHashMap> data, String uuid) {
        courseDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        List<Course> collect = data.stream().map(item -> gson.fromJson(gson.toJson(item), Course.class)
                .setUuid(uuid)
                .setAddDate(LocalDateTime.now())
                .setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N))
                .collect(Collectors.toList());

//        for (Course courses : collect) {
//            Course course = courseDao.findByUuidAndCourseIdAndLogicDelete(uuid, courses.getCourseId(), Constant.LogicDelete.LOGIC_DELETE_N);
//            if (null != course) {
//                courses.setClassCount((null == course.getClassCount() ? 0 : course.getClassCount()) + (null == courses.getClassCount() ? 0 : courses.getClassCount()));
//            }
//        }
        courseDao.saveAll(collect);
    }


    //TODO 新增之前删除
    private void saveHost(List<LinkedHashMap> data, String uuid) {
        hostDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        for (LinkedHashMap datum : data) {
            List<LinkedHashMap> list = (List) datum.get(DATA);
            List<Host> hostList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (LinkedHashMap o : list) {
                    Host host = new Host();
                    host.setHostHardWareType((String) datum.get("hostHardWareType"));
                    host.setHostId((String) o.get(ID));
                    host.setHostName((String) o.get(NAME));
                    host.setRate((String) o.get(RATE));
                    host.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                    host.setAddDate(LocalDateTime.now());
                    host.setUuid(uuid);
                    hostList.add(host);
                }
            }
            hostDao.saveAll(hostList);
        }
    }


    //TODO 新增之前删除
    private void saveVm(List<LinkedHashMap> data, String uuid) {
        vmDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        for (LinkedHashMap datum : data) {
            List<LinkedHashMap> list = (List) datum.get(DATA);
            List<Vm> vmList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(list)) {
                for (LinkedHashMap o : list) {
                    Vm vm = new Vm();
                    vm.setVmHardWareType((String) datum.get("vmHardWareType"));
                    vm.setVmId((String) o.get(ID));
                    vm.setVmName((String) o.get(NAME));
                    vm.setRate((String) o.get(RATE));
                    vm.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                    vm.setAddDate(LocalDateTime.now());
                    vm.setUuid(uuid);
                    vmList.add(vm);
                }
            }
            vmDao.saveAll(vmList);
        }

    }

    //TODO 软件热力指度
    private void saveSoftware(List<LinkedHashMap> data, String uuid) {
        softwareDao.deleteByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        softwareDao.saveAll(data.stream().map(item -> gson.fromJson(gson.toJson(item), Software.class)
                .setUuid(uuid)
                .setAddDate(LocalDateTime.now())
                .setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N))
                .collect(Collectors.toList()));
    }

    private void saveVersionInfo(List<LinkedHashMap> data, String uuid) {
        //序列化
//        JsonSerializer<LocalDate> jsonSerializer =
//                (LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext)
//                        -> new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
//        //反序列化
//        JsonDeserializer<LocalDate> jsonDeserializer =
//                (JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
//                        -> LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
        versionInfoDao.saveAll(data
                .stream()
                .map(item -> gson.fromJson(gson.toJson(item), VersionInfo.class)
                        .setUuid(uuid)
                        .setAddDate(LocalDateTime.now())
                        .setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N))
                .collect(Collectors.toList()));
    }

    private void saveCollegeDynamic(List<LinkedHashMap> data, String uuid) {

        String key = "6_" + uuid;
        String value = "lock_" + uuid;
        if (redisUtil.setIfPresent(key, value, 300)) {
            log.info("上锁...............SUCCESS: {}", key);
            data.stream()
                    .map(item -> {
                        Gson gson = new Gson()
                                .newBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                                .create();
                        return gson
                                .fromJson(gson.toJson(item), CollegeDynamic.class)
                                .setUuid(uuid)
                                .setAddDate(LocalDateTime.now())
                                .setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                    }).collect(Collectors.toList()).forEach(var -> {
                //先查询上线信息是否有变化 如果查到说明无变化直接省略，如果没查到说明有变化新增数据
                if (Objects.isNull(collegeDynamicDao.findByUuidAndOnlineDateAndHostNumberAndClassroomNumberAndTerminalNumberAndLogicDelete
                        (uuid, var.getOnlineDate(), var.getHostNumber(), var.getClassroomNumber(), var.getTerminalNumber(), Constant.LogicDelete.LOGIC_DELETE_N))) {
                    collegeDynamicDao.save(var);
                }
            });

            //释放锁
            redisUtil.del(key);
            log.info("释放锁...............SUCCESS: {}", key);
//            String str = (String) redisUtil.get(key);
//            if (str.equals(value)){
//                redisUtil.del(key);
//                log.info("释放锁...............SUCCESS: {}", key);
//            }
        } else {
            log.info("锁资源占用中...............SUCCESS: {}", key);
        }
    }

    private void saveCollegeLastDynamic(List<LinkedHashMap> data, String uuid) {
        collegeLastDynamicDao.saveAll(data
                .stream()
                .map(item -> {
                    Gson gson = new Gson()
                            .newBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                            .create();
                    return gson
                            .fromJson(gson.toJson(item), CollegeLastDynamic.class)
                            .setUuid(uuid)
                            .setAddDate(LocalDateTime.now())
                            .setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                })
                .collect(Collectors.toList()));
    }


    @PostConstruct
    private void init() {
        eventHandler.addListener(EventType.DATA_SYNC, this::dataSync);
        eventHandler.addListener(EventType.SITE_SYNC, this::siteSync);
    }

    //todo 地点同步
    private synchronized void siteSync(RequestData<ShareArea> data) {

        if (Objects.nonNull(data) && Objects.nonNull(data.getData())) {
            ShareArea var = data.getData();
            List<OnLineInfo> onLineInfos = onLineInfoDao.findByUuidAndLogicDelete(var.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
            if (CollectionUtils.isNotEmpty(onLineInfos)) {
                onLineInfos.forEach(item -> {
                    item.setProvinceCode(var.getProvinceCode());
                    item.setProvinceName(var.getProvinceName());
                    item.setCityCode(var.getCityCode());
                    item.setCityName(var.getCityName());
                    item.setCountyCode(var.getCountyCode());
                    item.setCountyName(var.getCountyName());
                    item.setReserver1(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_TIME_PATTERN)) + "_site_sync");
                });
                onLineInfoDao.saveAll(onLineInfos);
            }

        }
    }


    //todo   异步执行事件任务数据同步
    private synchronized void dataSync(RequestData<List<CollegeInfo>> data) {

        //同步数据
        if (Objects.nonNull(data) && CollectionUtils.isNotEmpty(data.getData())) {

            //同步tbl_share_area统计数据
            syncShareArea(data.getData());

            //同步或新增tbl_on_line_info每日在线数据
            syncOnLineInfo(data.getData());

            //计算上课总时长
            countTotalTime();
        }
    }

    private void countTotalTime() {

        Long timeAll = shareAreaDao.timeAll();
        TotalTime totalTime = totalTimeDao.findByLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);

        if (null == totalTime) {
            TotalTime time = new TotalTime();
            time.setTotalTimeNationwide(timeAll.toString());
            time.setAddDate(LocalDateTime.now());
            time.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
            totalTimeDao.save(time);
        } else {
            totalTime.setTotalTimeNationwide(timeAll.toString());
            totalTime.setModifiedDate(LocalDateTime.now());
            totalTimeDao.save(totalTime);
        }

    }


    private void syncShareArea(List<CollegeInfo> data) {
        for (CollegeInfo collegeInfo : data) {
            ShareArea shareArea = shareAreaDao.findByUuidAndLogicDelete(collegeInfo.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
            shareArea.setClassSum(collegeInfo.getClassroomSum() == null ? 0 : collegeInfo.getClassroomSum());
            shareArea.setDesktopSum(collegeInfo.getDesktopTotal() == null ? 0 : collegeInfo.getDesktopTotal());
            shareArea.setCourseSum(collegeInfo.getCourseSum() == null ? 0 : collegeInfo.getCourseSum());
            shareArea.setUserSum(collegeInfo.getUserSum() == null ? 0 : collegeInfo.getUserSum());
            shareArea.setTerminalSum(collegeInfo.getTerminalTotal() == null ? 0 : collegeInfo.getTerminalTotal());
            shareArea.setHostSum(collegeInfo.getHostSum() == null ? 0 : collegeInfo.getHostSum());
            shareArea.setAttendClassSum(collegeInfo.getClassCount() == null ? 0 : collegeInfo.getClassCount());
            shareArea.setAttendClassTime(collegeInfo.getClassTime() == null ? 0 : collegeInfo.getClassTime());
            shareArea.setModifiedDate(LocalDateTime.now());
            shareAreaDao.save(shareArea);
        }
    }

    private void syncOnLineInfo(List<CollegeInfo> data) {

        for (CollegeInfo collegeInfo : data) {

            OnLineInfo info = onLineInfoDao.findByUuidAddDate(collegeInfo.getUuid(), LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN)));
            if (Objects.isNull(info)) {
                //通过uuid拿到省市县
                ShareArea shareArea = shareAreaDao.findByUuidAndLogicDelete(collegeInfo.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
                OnLineInfo entity = new OnLineInfo();
                entity.setUuid(collegeInfo.getUuid());
                entity.setProvinceCode(shareArea.getProvinceCode());
                entity.setProvinceName(shareArea.getProvinceName());
                entity.setCityCode(shareArea.getCityCode());
                entity.setCityName(shareArea.getCityName());
                entity.setCountyCode(shareArea.getCountyCode());
                entity.setCountyName(shareArea.getCountyName());
                entity.setStatisticsDate(LocalDate.now());
                entity.setOnLineDesktopSum(collegeInfo.getDesktopRunningLsSum() == null ? 0 : collegeInfo.getDesktopRunningLsSum());
                entity.setOnLineTerminalSum(collegeInfo.getTerminalOnlineSum() == null ? 0 : collegeInfo.getTerminalOnlineSum());
                entity.setAttendClassSum(collegeInfo.getClassCountPerDay() == null ? 0 : collegeInfo.getClassCountPerDay());
                entity.setAddDate(LocalDateTime.now());
                entity.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                onLineInfoDao.save(entity);
            } else {

                //todo 更新在线桌面数和终端数为最大值
                long lsSum = collegeInfo.getDesktopRunningLsSum() == null ? 0 : collegeInfo.getDesktopRunningLsSum();
                long maxDesk = info.getOnLineDesktopSum() == null ? 0 : info.getOnLineDesktopSum();

                if (lsSum > maxDesk) {
                    //更新在线桌面数为最大值
                    info.setOnLineDesktopSum(lsSum);
                }

                long terminalSum = collegeInfo.getTerminalOnlineSum() == null ? 0 : collegeInfo.getTerminalOnlineSum();//上报过来的
                long maxTerminal = info.getOnLineTerminalSum() == null ? 0 : info.getOnLineTerminalSum();  //已存储的

                if (terminalSum > maxTerminal) {
                    //更新在线终端数为最大值
                    info.setOnLineTerminalSum(terminalSum);
                }
                info.setAttendClassSum(collegeInfo.getClassCountPerDay());
                info.setModifiedDate(LocalDateTime.now());
                onLineInfoDao.save(info);
            }
        }
    }


    static void asyncExecute1() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> log.info("---async execute 1 ---"));
        executorService.shutdown();
    }

    static void asyncExecute2() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(() -> log.info("---async execute 2 ---"));
        executorService.shutdown();
    }


    static void asyncExecute3() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        executorService.execute(() -> log.info("---async execute 3 ---"));
        executorService.shutdown();
    }

    static void asyncExecute4() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> log.info("---async execute 4 ---"));
        executorService.shutdown();
    }


    public static void main(String[] args) {
/*
        //(1)同步执行
        System.out.println("---sync execute---");
        //(2)异步执行操作1
        asyncExecute1();
        //(3)异步执行操作2
        asyncExecute2();
        //(4)异步执行操作3
        asyncExecute3();
        //(5)异步执行操作4
        asyncExecute4();
        //(5)执行完毕
        System.out.println("---execute over---");
*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread().getName() + ":使用正常方法创建线程");
            }
        }, "正常方法创建线程").start();

        new Thread(() -> {
            log.info(Thread.currentThread().getName() + ":使用lambda表达式创建线程");

        }, "lambda表达式创建线程").start();

        new Thread(new MyThread(), "Runnable创建线程").start();

    }

    private static class MyThread implements Runnable {
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + ":实现Runnable创建线程");
        }
    }

}


