package com.h3c.vdi.viewscreen.service.file;

import cn.hutool.core.util.IdUtil;
import com.h3c.vdi.viewscreen.api.model.request.file.RequestUploadFile;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.LogoCustomDao;
import com.h3c.vdi.viewscreen.dao.UploadFileRecordsDao;
import com.h3c.vdi.viewscreen.entity.LogoCustom;
import com.h3c.vdi.viewscreen.entity.UploadFileRecords;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Date 2020/10/19 14:53
 * @Created by lgw2845
 */

@Log4j2
@Service
public class UploadFileRecordsServiceImpl implements UploadFileRecordsService {


    /**
     * 先设定一个放置上传文件的文件夹(该文件夹可以不存在，下面会判断创建)
     */
    @Value("${uploadSavePath}")
    private String uploadSavePath;

    @Resource
    private UploadFileRecordsDao uploadFileRecordsDao;

    @Resource
    private LogoCustomDao logoCustomDao;


    /**
     * 取得包含扩展名的文件名
     *
     * @param originalFilename
     * @return
     */
    public String getFileName(String originalFilename) {
        String returnValue = originalFilename;
        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如D:\\1\\myImage.png)
        // String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = originalFilename.lastIndexOf("\\");
        if (index > 0) {
            returnValue = originalFilename.substring(index + 1);
        }

        return returnValue;
    }

    /**
     * 取得文件扩展名
     *
     * @param fileName
     * @return
     */
    public String getFileExtensionName(String fileName) {
        String returnValue = null;

        int index = fileName.lastIndexOf(".");
        // 当文件无后缀名时(如D盘下的hosts文件就没有后缀名)
        if (index < 0) {
            return null;
        }

        // 当文件有后缀名时
        if (index >= 0) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            // String[] fileNameSplitArray = fileName.split("\\.");
            // 加上random戳,防止附件重名覆盖原文件
            // String uuid = UUID.randomUUID().toString().replace("-","");
            // fileName = fileNameSplitArray[0] + uuid + "." + fileNameSplitArray[1];
            returnValue = fileName.substring(index);
        }

        return returnValue;
    }

    /**
     * 保存上传的文件到指定路径
     *
     * @param file
     * @param fileSavePath
     * @return
     */
    public boolean saveUploadFile(MultipartFile file, String fileSavePath) {
        boolean returnValue = false;

        try {
            File saveFile = new File(fileSavePath);
            // 如果pathAll路径不存在，则创建相关该路径涉及的文件夹;
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(saveFile);
            returnValue = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }


    //保存文件上传
    @Override
    public UploadFileRecords saveUploadFile(RequestUploadFile requestParam) {
        UploadFileRecords returnValue = null;

        try {
            MultipartFile file = requestParam.getFile();
            log.info("********** file.getOriginalFilename():" + file.getOriginalFilename());

            String originalFilename = this.getFileName(Objects.requireNonNull(file.getOriginalFilename()));
            log.info("********** originalFilename:" + originalFilename);
            String fileExtensionName = this.getFileExtensionName(originalFilename);
            log.info("********** fileExtensionName:" + fileExtensionName);
            // 加上random戳,防止附件重名覆盖原文件
            String fileName = originalFilename.substring(0, originalFilename.length() - fileExtensionName.length());
            log.info("********** fileName:" + fileName);
            //fileName = fileName + "_" + IdUtil.simpleUUID + fileExtensionName;
            fileName = IdUtil.simpleUUID() + fileExtensionName;
//            String filePath = uploadSavePath + requestParam.getSource() + LocalDate.now().format(DateTimeFormatter.ofPattern("/yyyy/MM/dd/"));

            String filePath = "D://" + requestParam.getSource() + LocalDate.now().format(DateTimeFormatter.ofPattern("/yyyy/MM/dd/"));

            // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
            String fileSavePath = filePath + fileName;
            log.info("********** fileSavePath:" + fileSavePath);

            if (this.saveUploadFile(file, fileSavePath)) {
                // 保存到数据库
                returnValue = new UploadFileRecords();
                returnValue.setSrcFileName(originalFilename);
                returnValue.setSource(requestParam.getSource());
                returnValue.setFilePath(filePath);
                returnValue.setFileName(fileName);
                returnValue.setFileExtension(fileExtensionName);
                returnValue.setFileSize(requestParam.getFileSize());
                returnValue.setAddDate(LocalDateTime.now());
                returnValue.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                this.uploadFileRecordsDao.save(returnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }


    //图片上传
    @Override
    public ApiResponse<String> imageUpload(MultipartFile file) {

        ApiResponse<String> returnValue = ApiResponse.buildError(ApiErrorEnum.E50000, "");

        // 判断该文件是否有内容
        if (Objects.isNull(file) || file.isEmpty()) {
            return ApiResponse.buildError(ApiErrorEnum.E50101, "file");
        }

        //判断文件大小
        long fileSize = file.getSize();
        log.info("file.getSize():" + fileSize);
        if (fileSize > 104857600) { // 104857600 = 100 * 1024 * 1024 100MB
            return ApiResponse.buildError(ApiErrorEnum.E50102, "100MB");
        }

        try {

            LogoCustom var = logoCustomDao.showPicture();
            if (Objects.nonNull(var)) {
                var.setImageName(file.getOriginalFilename());
                var.setTemporaryImage(file.getBytes());
                var.setModifiedDate(LocalDateTime.now());
                logoCustomDao.save(var);
            } else {
                LogoCustom data = new LogoCustom();
                data.setImageName(file.getOriginalFilename());
                data.setTemporaryImage(file.getBytes());
                data.setLogicDelete(Constant.LogicDelete.LOGIC_DELETE_N);
                data.setAddDate(LocalDateTime.now());
                logoCustomDao.save(data);
            }
            returnValue = ApiResponse.buildSuccess("图片上传成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


}
