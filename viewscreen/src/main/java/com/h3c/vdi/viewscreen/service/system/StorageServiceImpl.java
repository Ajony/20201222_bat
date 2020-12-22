/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: StorageColonyInfoService
 * Author:   ykf8829
 * Date:     2020/6/17 18:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.h3c.vdi.viewscreen.service.system;

import com.h3c.vdi.viewscreen.api.model.response.college.StorageDto;
import com.h3c.vdi.viewscreen.api.model.response.storage.StorageCluster;
import com.h3c.vdi.viewscreen.api.model.response.storage.ResponseData;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.common.rest.WorkspaceRestClient;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.constant.WsUriConstants;
import com.h3c.vdi.viewscreen.dao.ShareAreaDao;
import com.h3c.vdi.viewscreen.dao.StorageColonyInfoDao;
import com.h3c.vdi.viewscreen.entity.ShareArea;
import com.h3c.vdi.viewscreen.entity.StorageColonyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ykf8829
 * @create 2020/6/17
 * @since 1.0.0
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageColonyInfoDao storageColonyInfoDao;


    @Resource
    private WorkspaceRestClient workspaceRestClient;

    @Resource
    private ShareAreaDao shareAreaDao;

    @Override
    public ApiResponse<List<StorageDto>> storageList(String uuid) {
        ApiResponse<List<StorageDto>> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");
        List<StorageDto> storageDtoList = storageDtoList(uuid);
        if (!storageDtoList.isEmpty()) {
            returnValue = ApiResponse.buildSuccess(storageDtoList);
        }
        return returnValue;
    }

    private List<StorageDto> storageDtoList(String uuid) {
        String beginTime = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
        String endTime = LocalDate.now().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_PATTERN));
        storageColonyInfoDao.deleteByAddDate(uuid, beginTime, endTime);
        List<StorageColonyInfo> byUuidAndTime = storageColonyInfoDao.findByUuidAndTime(uuid, beginTime, endTime);
        return storageColonyInfoToDto(byUuidAndTime);
    }

    private List<StorageDto> storageColonyInfoToDto(List<StorageColonyInfo> list) {
        if (!list.isEmpty()) {
            List<StorageDto> storageDtoList = new LinkedList<>();
            for (int i = 0; i < list.size(); i++) {
                if ((list.size() - 1 - i) % Constant.ReportCount.REPORT_COUNT == 0 && i >= list.size() - 1 - 23 * Constant.ReportCount.REPORT_COUNT) {
                    StorageDto storageDto = new StorageDto();
                    storageDto.setMbps(list.get(i).getIoThroughputCapacity());
                    storageDto.setPercent(list.get(i).getIopsPercent());
                    storageDto.setTime(list.get(i).getTime().format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.HOUR_MINUTE)));
                    storageDtoList.add(storageDto);
                }
            }
            return storageDtoList;
        }
        return new LinkedList<>();
    }


    @Override
    public ApiResponse<ResponseData> ioPs(String uuid) {
        ApiResponse<ResponseData> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");

        ShareArea data = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.nonNull(data)) {
            String ip = data.getIp();
            try {
                String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Storage.STORAGE_IOPS;
                ResponseEntity<ApiResponse<StorageCluster>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ApiResponse<StorageCluster>>() {
                });
                Optional<StorageCluster> ioPs = Optional.ofNullable(result.getBody()).map(ApiResponse::getData);
                if (ioPs.isPresent()) {
                    StorageCluster var = ioPs.get();
                    log.info("io_ps success {}", var.toString());
                    if (uuid.equals(var.getUuid())) {
                        List<StorageCluster.ClusterData.TrendRate> rate = Optional.ofNullable(var.getClusterData())
                                .map(StorageCluster.ClusterData::getTrendRate)
                                .orElse(null);

                        if (Objects.nonNull(rate)) {
                            returnValue = ApiResponse.buildSuccess(new ResponseData(rate));
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return returnValue;
    }


    @Override
    public ApiResponse<ResponseData> io(String uuid) {
        ApiResponse<ResponseData> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");

        ShareArea data = shareAreaDao.findByUuidAndLogicDelete(uuid, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.nonNull(data)) {
            String ip = data.getIp();
            try {
                String url = "http://" + ip + ":" + WsUriConstants.WsPort + WsUriConstants.Storage.STORAGE_IO;
                ResponseEntity<ApiResponse<StorageCluster>> result = workspaceRestClient.workspaceRestClient(ip).getRestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ApiResponse<StorageCluster>>() {
                });
                Optional<StorageCluster> io = Optional.ofNullable(result.getBody()).map(ApiResponse::getData);
                if (io.isPresent()) {
                    StorageCluster var = io.get();
                    log.info("io success {}", var.toString());
                    if (uuid.equals(var.getUuid())) {

                        Optional<List<StorageCluster.ClusterData.TrendRate>> rate = Optional.ofNullable(var.getClusterData())
                                .map(StorageCluster.ClusterData::getTrendRate);

                        if (rate.isPresent()) {
                            returnValue = ApiResponse.buildSuccess(new ResponseData(rate.get()));
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return returnValue;
    }


}
