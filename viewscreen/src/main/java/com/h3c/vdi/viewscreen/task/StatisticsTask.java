package com.h3c.vdi.viewscreen.task;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.gson.Gson;
import com.h3c.vdi.viewscreen.config.LocalDateAdapter;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.*;
import com.h3c.vdi.viewscreen.entity.ChinaArea;
import com.h3c.vdi.viewscreen.entity.OnLineInfo;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lgq
 * @since 2020/6/15 16:18
 * 定时在每天凌晨一点统计上报过来的数据
 */
@Component
@Slf4j
@EnableAsync
public class StatisticsTask {

    @Resource
    private ShareAreaDao shareAreaDao;

    @Resource
    private CollegeInfoDao collegeInfoDao;

    @Resource
    private TotalTimeDao totalTimeDao;

    @Resource
    private ChinaAreaDao chinaAreaDao;

    @Resource
    private OnLineInfoDao onLineInfoDao;

    @Resource
    private TerminalHistoryDao terminalHistoryDao;

    private final Gson gson = new Gson();


    /**
     * 统计全国数据    策略每天一次
     */
    @Async
    @Scheduled(cron = "0 1 0 * * ?")
//    @Scheduled(cron = "0 */10 * * * ?")
    public void scheduledTask1() {
        log.info("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "=============== 线程 : " + Thread.currentThread().getName());
//        log.info("================================================[START：定时任务开始]================================================");
//        log.info("================================================[START：定时任务开始]================================================");
//        log.info("================================================[START：定时任务开始]================================================");

        //todo 更新昨天上报的数据
//      updateReportData(LocalDate.now().minusDays(1).toString());
//        updateReportData();

        //todo 计算上课总时长
//        countTotalTime();

        //todo 更新全国统计数据  更新之前需要把之前的数据先清理掉
//        updateAllStatisticsData();

        //todo 更新 tbl_on_line_info  更新之前先清理
//        updateOnLineInfo();
//        log.info("================================================[END：定时任务结束]================================================");
//        log.info("================================================[END：定时任务结束]================================================");
//        log.info("================================================[END：定时任务结束]================================================");

    }


//每月最后一天23点30执行一次
//    @Async
//    @Scheduled(cron = "30 30 23 28-31 * ?")
//    public void scheduledTask2() {
//        final Calendar c = Calendar.getInstance();
//        log.info(String.valueOf(c.get(Calendar.DATE)));
//        log.info(String.valueOf(c.getActualMaximum(Calendar.DATE)));
//        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
//            log.info("============================================================={是最后一天！}");
//
//            LocalDateTime now = LocalDateTime.now();
//            onLineInfoDao.clearHistoryData(now.minusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE),
//                    now.format(DateTimeFormatter.ISO_LOCAL_DATE));
//        }
//    }


    @Async
    @Scheduled(cron = "0 1 0 * * ?")
    public void scheduledTask2() {
        LocalDateTime now = LocalDateTime.now();
        onLineInfoDao.clearHistoryData(now.minusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE),
                now.format(DateTimeFormatter.ISO_LOCAL_DATE));

        terminalHistoryDao.clearHistoryData(now.minusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE),
                now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        log.info("================[success ：清理历史数据]=================== {}",LocalDateTime.now().toString());
    }


//    private void updateReportData(String yesterday) {
//        log.info("==============================[START：更新数据]==============================");
//        List<Map<String, Object>> mapList = collegeInfoDao.queryYesterdayInfo(yesterday);
//        if (CollectionUtils.isNotEmpty(mapList)) {
//            List<ShareArea> collect = mapList.stream().map(item -> gson.fromJson(gson.toJson(item), ShareArea.class)).collect(Collectors.toList());
//            for (ShareArea shareArea : collect) {
//                ShareArea area = shareAreaDao.findByUuidAndLogicDelete(shareArea.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
//                BeanUtil.copyProperties(shareArea, area, CopyOptions.create().setIgnoreNullValue(true));
//                area.setModifiedDate(LocalDateTime.now());
//                shareAreaDao.save(area);
//            }
//        }
//
//        log.info("==============================[END：更新数据]==============================");
//    }

    private void updateReportData() {
        log.info("==============================[START：更新数据]==============================");
        List<Map<String, Object>> mapList = collegeInfoDao.queryYesterdayInfo();
        if (CollectionUtils.isNotEmpty(mapList)) {
            List<ShareArea> collect = mapList.stream().map(item -> gson.fromJson(gson.toJson(item), ShareArea.class)).collect(Collectors.toList());
            for (ShareArea shareArea : collect) {
                ShareArea area = shareAreaDao.findByUuidAndLogicDelete(shareArea.getUuid(), Constant.LogicDelete.LOGIC_DELETE_N);
                BeanUtil.copyProperties(shareArea, area, CopyOptions.create().setIgnoreNullValue(true));
                area.setModifiedDate(LocalDateTime.now());
                shareAreaDao.save(area);
            }
        }

        log.info("==============================[END：更新数据]==============================");
    }


    private void updateAllStatisticsData() {
        log.info("==============================[START：统计全国数据]==============================");
        //先清理之前的数据
//        chinaAreaDao.clearHistoryData();
        log.info("==============================[CLEAR-HISTORY-DATA-SUCCESS]==============================");

        //重新统计最新数据
        log.info("==============================[START：统计省]==============================");
        statisticsProvince();
        log.info("==============================[END：统计省]==============================");


        log.info("==============================[START：统计市]==============================");
        statisticsCity();
        log.info("==============================[END：统计市]==============================");


        log.info("==============================[START：统计县]==============================");
        statisticsCounty();
        log.info("==============================[END：统计县]==============================");


        log.info("==============================[END：计算全国数据]==============================");
        countNationwide();
        log.info("==============================[END：计算全国]==============================");


        log.info("==============================[END：统计全国数据]==============================");

    }


//    private void countTotalTime() {
//        log.info("==============================[START：统计上课总时长]==============================");
//        Long timeAll = shareAreaDao.timeAll();
//
//        TotalTime totalTime = totalTimeDao.findByLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
//
//        if (null == totalTime) {
//            TotalTime time = new TotalTime();
//            time.setTotalTimeNationwide(timeAll.toString());
//            time.setAddDate(LocalDateTime.now());
//            time.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
//            totalTimeDao.save(time);
//        } else {
//            totalTime.setTotalTimeNationwide(timeAll.toString());
//            totalTime.setModifiedDate(LocalDateTime.now());
//            totalTimeDao.save(totalTime);
//        }
//
//        log.info("==============================[END：统计上课总时长]==============================");
//    }


    private void statisticsProvince() {

        List<Map<String, Object>> provinceSchoolSum = shareAreaDao.provinceSchoolSum();
        if (CollectionUtils.isNotEmpty(provinceSchoolSum))
            updateSum(provinceSchoolSum.stream().map(item -> gson.fromJson(gson.toJson(item), ChinaArea.class)).collect(Collectors.toList()), 1);

        List<Map<String, Object>> provinceRestSum = shareAreaDao.provinceRestSum();
        if (CollectionUtils.isNotEmpty(provinceRestSum))
            updateSum(provinceRestSum.stream().map(item -> gson.fromJson(gson.toJson(item), ChinaArea.class)).collect(Collectors.toList()),1);

        log.info("=======================================[省份]===============================");
    }


    private void statisticsCity() {
        List<Map<String, Object>> citySchoolSum = shareAreaDao.citySchoolSum();
        if (CollectionUtils.isNotEmpty(citySchoolSum))
            updateSum(citySchoolSum.stream().map(item -> gson.fromJson(gson.toJson(item), ChinaArea.class)).collect(Collectors.toList()), 2);

        List<Map<String, Object>> cityRestSum = shareAreaDao.cityRestSum();
        if (CollectionUtils.isNotEmpty(cityRestSum))
            updateSum(cityRestSum.stream().map(item -> gson.fromJson(gson.toJson(item), ChinaArea.class)).collect(Collectors.toList()), 2);


        log.info("=======================================[市区]===============================");
    }


    private void statisticsCounty() {
        List<Map<String, Object>> countySchoolSum = shareAreaDao.countySchoolSum();
        if (CollectionUtils.isNotEmpty(countySchoolSum))
            updateSum(countySchoolSum.stream().map(item -> gson.fromJson(gson.toJson(item), ChinaArea.class)).collect(Collectors.toList()), 3);

        List<Map<String, Object>> countyRestSum = shareAreaDao.countyRestSum();
        if (CollectionUtils.isNotEmpty(countyRestSum))
            updateSum(countyRestSum.stream().map(item -> gson.fromJson(gson.toJson(item), ChinaArea.class)).collect(Collectors.toList()),3);


        log.info("=======================================[区县]===============================");
    }


    private void countNationwide() {
        ChinaArea chinaArea = chinaAreaDao.findByAddressParentIdAndAddressTypeAndLogicDelete(0L, 0, Constant.LogicDelete.LOGIC_DELETE_N);
        BeanUtil.copyProperties(gson.fromJson(gson.toJson(chinaAreaDao.summation()), ChinaArea.class), chinaArea, CopyOptions.create().setIgnoreNullValue(true));
        chinaArea.setModifiedDate(LocalDateTime.now());
        chinaAreaDao.save(chinaArea);
    }


    private void updateSum(List<ChinaArea> collect, Integer addressType) {
        for (ChinaArea area : collect) {
            ChinaArea chinaArea = chinaAreaDao.findByAddressCodeAndAddressTypeAndLogicDelete(area.getAddressCode(), addressType, Constant.LogicDelete.LOGIC_DELETE_N);
            if (null != chinaArea) {
                area.setAddressCode(null);
                area.setAddressName(null);
                BeanUtil.copyProperties(area, chinaArea, CopyOptions.create().setIgnoreNullValue(true));
                chinaArea.setModifiedDate(LocalDateTime.now());
                chinaAreaDao.save(chinaArea);
            }
        }
    }


    private void updateOnLineInfo() {

        log.info("==============================[START：更新TBL_ON_LINE_INFO]==============================");
        onLineInfoDao.deleteAll();
        LocalDate nowDate = LocalDate.now();
        List<Map<String, Object>> uuidList = collegeInfoDao.queryUuidList();

        if (CollectionUtils.isNotEmpty(uuidList)) {
            List<ShareArea> collect = uuidList.stream().map(item -> gson.fromJson(gson.toJson(item), ShareArea.class)).collect(Collectors.toList());

            List<OnLineInfo> onLineInfoArrayList = new ArrayList<>();
            for (ShareArea shareArea : collect) {
                List<Map<String, Object>> infoList = collegeInfoDao
                        .queryNearTenData(nowDate.minusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE),
                                nowDate.minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE), shareArea.getUuid());

                if (CollectionUtils.isNotEmpty(infoList)) {
                    List<OnLineInfo> collegeInfos = infoList.stream().map(item -> {
                        Gson gson = new Gson()
                                .newBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                                .create();
                        return gson
                                .fromJson(gson.toJson(item), OnLineInfo.class);
                    }).collect(Collectors.toList());
                    for (OnLineInfo info : collegeInfos) {
                        OnLineInfo onLineInfo = new OnLineInfo();
                        BeanUtil.copyProperties(shareArea, onLineInfo, CopyOptions.create().setIgnoreNullValue(true));
                        BeanUtil.copyProperties(info, onLineInfo, CopyOptions.create().setIgnoreNullValue(true));
                        onLineInfo.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                        onLineInfo.setAddDate(LocalDateTime.now());
                        onLineInfoArrayList.add(onLineInfo);
                    }
                }
            }
            onLineInfoDao.saveAll(onLineInfoArrayList);
        }
        log.info("==============================[END：更新TBL_ON_LINE_INFO]==============================");
    }


    public static void main(String[] args) {

        final long milliseconds = 199099000;

        System.out.println(milliseconds / (60 * 60 * 1000));

        final long day = TimeUnit.MILLISECONDS.toDays(milliseconds);

        final long hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));

        final long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));

        final long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));

        final long ms = TimeUnit.MILLISECONDS.toMillis(milliseconds)
                - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));

        log.info("milliseconds :-" + milliseconds);
        log.info(String.format("%d Days %d Hours %d Minutes %d Seconds %d Milliseconds", day, hours, minutes, seconds, ms));
        log.info("毫秒 :-" + milliseconds);
        log.info(String.format("%d 天 %d 小时 %d 分钟 %d 秒 %d 毫秒", day, hours, minutes, seconds, ms));

//        1、时间戳（毫秒）
//          System.currentTimeMillis()
//        2、时间戳（转秒）
//         System.currentTimeMillis() / 1000
//        3、时间戳（转分钟）
//        System.currentTimeMillis() / 1000 / 60
//        4、时间戳（转小时）
//        System.currentTimeMillis() / 1000 / (60 * 60)
//        5、时间戳（转天）
//        System.currentTimeMillis() / 1000 / (60 * 60 * 24)


        final Calendar c = Calendar.getInstance();
        log.info(String.valueOf(c.get(Calendar.DATE)));
        log.info(String.valueOf(c.getActualMaximum(Calendar.DATE)));


    }


}
