package com.h3c.vdi.viewscreen.web;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import com.h3c.vdi.viewscreen.api.model.response.autonavi.ResponseAdCode;
import com.h3c.vdi.viewscreen.api.model.response.autonavi.ResponseLocation;
import com.h3c.vdi.viewscreen.api.model.response.monitoring.ResponseMonitoring;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.RestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.ChinaAreaDao;
import com.h3c.vdi.viewscreen.dao.CollegeInfoDao;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dao.UserDao;
import com.h3c.vdi.viewscreen.entity.ChinaArea;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.entity.User;
import com.h3c.vdi.viewscreen.module.kafka.KafkaWeb;
import com.h3c.vdi.viewscreen.service.monitoring.MonitoringService;
import com.h3c.vdi.viewscreen.utils.httpclient.HttpDefaultUtil;
import com.h3c.vdi.viewscreen.utils.httpclient.HttpProxyUtil;
import com.h3c.vdi.viewscreen.utils.sm4.SM4Utils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
public class JunitTest {

    @Resource
    KafkaWeb kafkaWeb;

//    @Resource
//    ReportController reportController;

    @Resource
    CollegeInfoDao collegeInfoDao;

    @Resource
    MonitoringService monitoringService;

    @Resource
    UserDao userDao;


    @Test
    public void test1() {
        String protocol = "http";//workspaceRestProperties.getProtocol();
        String host = "10.125.8.16";//workspaceRestProperties.getHost();
        int port = 8083;//workspaceRestProperties.getPort();
        int maxTotalConnection = 100;//workspaceRestProperties.getMaxTotalConnection();
        int perRouteConnection = 100;//workspaceRestProperties.getPerRouteConnection();
        String username = "root";//VdiConstants.RESOURCE_BUNDLE.getString("WORKSPACE_REST_USERNAME");
        String password = "root@123";//VdiConstants.RESOURCE_BUNDLE.getString("WORKSPACE_REST_PASSWORD");
        RestClient restClient = new RestClient(protocol, host, port, username, password, maxTotalConnection, perRouteConnection);

    }

    @Test
    public void getUser() {
    }


    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("test");
        user.setLogicDelete("n");
        user.setAddDate(LocalDateTime.now());
        user.setDescription("Cloud@1234");
        user.setPassword(SM4Utils.webEncryptText("Cloud@1234"));
        userDao.save(user);

    }


    @Test
    public void kafkaMessage() {
//        kafkaWeb.send();
    }


//    @Test
//    public void report() {
//
//        /*User user = new User();
//        user.setUuid("UUID-666666666666666");
//        user.setUserId(1l);
//        user.setUserName("牛逼哥");*/
//
//        List<RequestReport> reportList = new ArrayList<>();
//        /*String toJson = new Gson().toJson(user);
//        List<String> list = new ArrayList();
//        list.add(toJson);*/
//
//        RequestReport requestReport = new RequestReport();
//        requestReport.setType(0);
//        //    requestReport.setDataList(list);
//        reportList.add(requestReport);
//        //reportController.dataReport(reportList);
//    }


    @Test
    public void queryUuidList() {
        List<Map<String, Object>> uuidList = collegeInfoDao.queryUuidList();
        uuidList.forEach(System.out::println);

    }


    @Test
    public void query() {
        ApiResponse<ResponseMonitoring> query = monitoringService.query();
        log.info(query.getData().toString());
    }

//    @Test
//    public void queryPage() {
//        ApiResponse<ApiPage<ResponsePageCollegeDynamic>> apiPageApiResponse = monitoringService.queryPage(1, 5);
//        List<ResponsePageCollegeDynamic> records = apiPageApiResponse.getData().getRecords();
//        records.forEach(System.out::println);
//    }


    @Test
    public void queryDetails() {
        ApiResponse<ResponseMonitoring> query = monitoringService.query("110000", 1);
        log.info(query.getData().toString());
    }


    @Test
    public void proxyCoordinate() {
        String address = "北京";
        ApiResponse<ResponseLocation> apiResponse = HttpDefaultUtil.addressLongitude(address);

        if (apiResponse != null && apiResponse.getData() != null) {
            log.info(apiResponse.getData().toString());
        }
        log.info("🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷");

    }

    @Test
    public void defaultCoordinate() {
        String address = "北京";
        ApiResponse<ResponseLocation> apiResponse = HttpDefaultUtil.addressLongitude(address);

        if (apiResponse != null && apiResponse.getData() != null) {
            log.info(apiResponse.getData().toString());
        }
        log.info("🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷🐷");

    }


    @Resource
    ChinaAreaDao chinaAreaDao;

    @Test
    public void readText() throws IOException {
        List<ChinaArea> list = chinaAreaDao.findAll();
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\lgw2845\\Desktop\\cityMap.txt"));
        for (ChinaArea chinaArea : list) {
            bw.write(chinaArea.getAddressCode() + ": " + "'" + chinaArea.getExtName() + "',");
            bw.newLine();
            bw.flush();
        }

        bw.close();
    }


    @Resource
    ShareAreaDao shareAreaDao;

    @Test
    public void importData() {

        shareAreaDao.deleteAll();

        List<ChinaArea> provinceItem = chinaAreaDao.queryArea();

        List<ShareArea> list = new ArrayList<>();

        List<ShareArea> data = new ArrayList<>();

        LocalDate now = LocalDate.now();

        Random rand = new Random();

        a:
        for (ChinaArea var1 : provinceItem) {

            List<ChinaArea> cityItem = chinaAreaDao.findByAddressParentIdAndLogicDelete(var1.getId(), Constant.LogicDelete.LOGIC_DELETE_N);
            b:
            for (ChinaArea var2 : cityItem) {
                List<ChinaArea> countyItem = chinaAreaDao.findByAddressParentIdAndLogicDelete(var2.getId(), Constant.LogicDelete.LOGIC_DELETE_N);
                c:
                for (ChinaArea var3 : countyItem) {

                    ShareArea shareArea = new ShareArea();

                    shareArea.setProvinceCode(var1.getAddressCode());
                    shareArea.setProvinceName(var1.getAddressName());
                    shareArea.setCityCode(var2.getAddressCode());
                    shareArea.setCityName(var2.getAddressName());
                    shareArea.setCountyCode(var3.getAddressCode());
                    shareArea.setCountyName(var3.getAddressName());
                    shareArea.setCollege(IdUtil.simpleUUID());
                    shareArea.setUuid(IdUtil.randomUUID());
                    shareArea.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                    shareArea.setAddDate(LocalDateTime.now());
                    shareArea.setOnlineDate(now.minusDays(rand.nextInt(1825) % (1825 - 1 + 1) + 1));
                    shareArea.setTerminalSum(299L + (rand.nextInt(9) % (9 - 1 + 1) + 1));
                    shareArea.setClassSum(7L + (rand.nextInt(5) % (5 - 3 + 1) + 3));
                    shareArea.setHostSum(105L + (rand.nextInt(9) % (9 - 2 + 1) + 2));
                    shareArea.setCourseSum(4L + (rand.nextInt(3) % (3 - 1 + 1) + 1));
                    shareArea.setUserSum(710L + (rand.nextInt(3) % (3 - 1 + 1) + 1));
                    shareArea.setDesktopSum(330L + (rand.nextInt(10) % (10 - 1 + 1) + 1));
                    shareArea.setAttendClassSum(8L);
                    shareArea.setAttendClassTime(6L);

                    list.add(shareArea);
                }
            }

            if (var1.getAddressName().equals("四川")) {
                data.addAll(list.subList(0, 144));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》120");

            } else if (var1.getAddressName().equals("山东")) {
                data.addAll(list.subList(0, 90));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》85");

            } else if (var1.getAddressName().equals("江苏")) {
                data.addAll(list.subList(0, 97));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》110");

            } else if (var1.getAddressName().equals("广东")) {
                data.addAll(list.subList(0, 95));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》75");

            } else if (var1.getAddressName().equals("广西")) {
                data.addAll(list.subList(0, 111));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》110");

            } else if (var1.getAddressName().equals("云南")) {
                data.addAll(list.subList(0, 108));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》110");

            } else if (var1.getAddressName().equals("内蒙古")) {
                data.addAll(list.subList(0, 60));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》56");

            } else if (var1.getAddressName().equals("西藏")) {
                data.addAll(list.subList(0, 23));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》23");

            } else if (var1.getAddressName().equals("新疆")) {
                data.addAll(list.subList(0, 21));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》20");

            } else if (var1.getAddressName().equals("黑龙江")) {
                data.addAll(list.subList(0, 18));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》18");

            } else if (var1.getAddressName().equals("重庆")) {
                data.addAll(list.subList(0, 20));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》14");

            } else if (var1.getAddressName().equals("宁夏")) {
                data.addAll(list.subList(0, 13));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》13");

            } else if (var1.getAddressName().equals("天津")) {
                data.addAll(list.subList(0, 12));
                System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》12");

            } else {
                if (var1.getAddressName().equals("北京") ||
                        var1.getAddressName().equals("上海") ||
                        var1.getAddressName().equals("香港") ||
                        var1.getAddressName().equals("澳门")) {
                    data.addAll(list);
                } else {
                    data.addAll(list.subList(0, rand.nextInt(25) % (25 - 23 + 1) + 23));
                }
            }

            System.out.println(var1.getAddressName() + "：" + list.size() + "-----------》25");
            list.clear();
        }
//        System.out.println(data.size());
//        data.forEach(System.out::println);
        shareAreaDao.saveAll(data);
    }


    @Test
    public void random() {
        int max = 25;
        int min = 23;
        Random random = new Random();

        for (int i = 1; i <= 100; i++) {
            int s = random.nextInt(max) % (max - min + 1) + min;
            System.out.println(s);
        }

        System.out.println("110000".substring(0, 2));
    }


    @Test
    public void syncAdCode() {
        chinaAreaDao.deleteAll();

        ArrayList<ChinaArea> list = new ArrayList<>();
        ChinaArea china = new ChinaArea();
        china.setId(1L);
        china.setAddressParentId(0L);
        china.setAddressName("中国");
        china.setAddressType(0);
        china.setAddressCode("0");
        china.setExtName("中国");
        china.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
        china.setAddDate(LocalDateTime.now());
        list.add(china);

        ArrayList<String> site = Lists.newArrayList("北京市", "天津市", "上海市", "重庆市",
                "河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省", "浙江省", "安徽省", "福建省", "江西省",
                "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省",
                "甘肃省", "青海省",
                "内蒙古自治区", "广西壮族自治区", "宁夏回族自治区", "新疆维吾尔自治区", "西藏自治区",
                "香港特别行政区", "澳门特别行政区");


        site.forEach(address -> {
            ApiResponse<ResponseAdCode> var = HttpProxyUtil.adCode(address);

            if (Objects.nonNull(var)) {

                //一级菜单
                List<ResponseAdCode.DistrictsBeanXX> province = var.getData().getDistricts();

                if (CollectionUtils.isNotEmpty(province)) {
                    province.forEach(a -> {
                        ChinaArea c1 = new ChinaArea();
                        Long provinceId = Long.valueOf(a.getAdcode().substring(0, 2));
                        c1.setId(provinceId);
                        c1.setAddressParentId(1L);
                        c1.setAddressName(a.getName());
                        c1.setAddressType(1);
                        c1.setAddressCode(a.getAdcode());
                        c1.setExtName(a.getName());
                        c1.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                        c1.setAddDate(LocalDateTime.now());
                        list.add(c1);


                        //二级菜单
                        List<ResponseAdCode.DistrictsBeanXX.DistrictsBeanX> city = a.getDistricts();
                        if (CollectionUtils.isNotEmpty(city)) {
                            city.forEach(b -> {
                                ChinaArea c2 = new ChinaArea();
                                long cityId;
                                if (CollectionUtils.isEmpty(b.getDistricts())) {
                                    cityId = Long.parseLong(b.getAdcode());
                                } else {
                                    cityId = Long.parseLong(b.getAdcode().substring(0, 4));
                                }
                                c2.setId(cityId);
                                c2.setAddressParentId(provinceId);
                                c2.setAddressName(b.getName());
                                c2.setAddressType(2);
                                c2.setAddressCode(b.getAdcode());
                                c2.setExtName(b.getName());
                                c2.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                                c2.setAddDate(LocalDateTime.now());
                                list.add(c2);


                                //三级菜单
                                List<ResponseAdCode.DistrictsBeanXX.DistrictsBeanX.DistrictsBean> county = b.getDistricts();
                                if (CollectionUtils.isNotEmpty(county)) {
                                    county.forEach(c -> {
                                        ChinaArea c3 = new ChinaArea();
                                        c3.setId(Long.valueOf(c.getAdcode()));
                                        c3.setAddressParentId(cityId);
                                        c3.setAddressName(c.getName());
                                        c3.setAddressType(3);
                                        c3.setAddressCode(c.getAdcode());
                                        c3.setExtName(c.getName());
                                        c3.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                                        c3.setAddDate(LocalDateTime.now());
                                        list.add(c3);

                                    });
                                }
                            });
                        }
                    });
                }
            }
            //有序插入数据
            chinaAreaDao.saveAll(list);
            list.clear();
        });

        list.forEach(System.out::println);
        //插入数据库的数据顺序很乱，所以不使用这种方式
//        chinaAreaDao.saveAll(list);
    }

    @Test
    public void testTread() throws InterruptedException {


        Thread thread1 = new Thread(() -> {
            ShareArea entity = new ShareArea();
            entity.setUuid("70e7a924-7f3e-4971-a0e1-4651f45da1d2");
            entity.setLogicDelete("n");
            entity.setAddDate(LocalDateTime.now());
            shareAreaDao.save(entity);
            System.out.println("lambda表达式创建线程111111");
        }, "lambda表达式创建线程111111");
        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(() -> {
            ShareArea entity = new ShareArea();
            entity.setUuid("70e7a924-7f3e-4971-a0e1-4651f45da1d2");
            entity.setLogicDelete("n");
            entity.setAddDate(LocalDateTime.now());
            shareAreaDao.save(entity);
            System.out.println("lambda表达式创建线程222222");
        }, "lambda表达式创建线程222222");
        thread2.start();
        thread2.join();


        Thread thread3 = new Thread(() -> {
            ShareArea entity = new ShareArea();
            entity.setUuid("70e7a924-7f3e-4971-a0e1-4651f45da1d2");
            entity.setLogicDelete("n");
            entity.setAddDate(LocalDateTime.now());
            shareAreaDao.save(entity);
            System.out.println("lambda表达式创建线程333333");
        }, "lambda表达式创建线程3333333");
        thread3.start();
        thread3.join();

    }


}