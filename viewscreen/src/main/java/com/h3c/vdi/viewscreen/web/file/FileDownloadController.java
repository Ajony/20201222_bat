package com.h3c.vdi.viewscreen.web.file;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.h3c.vdi.viewscreen.api.model.response.file.ResponseLogoCustom;
import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.api.result.ApiResponse;
import com.h3c.vdi.viewscreen.dao.LogoCustomDao;
import com.h3c.vdi.viewscreen.dao.UploadFileRecordsDao;
import com.h3c.vdi.viewscreen.entity.LogoCustom;
import com.h3c.vdi.viewscreen.utils.common.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Date 2020/8/5 9:29
 * @Created by lgw2845
 */
@RestController
@Log4j2
@Api(tags = "文件下载")
public class FileDownloadController {

    //    private static final String FILE_URL = "/file/ChromeSetup.exe";
    private static final String FILE_URL = "/file/chrome_installer.exe";
    private static final String DOWNLOAD_FAILURE = "下载失败";
    private static final String DOWNLOAD_SUCCESS = "下载成功";
    private static final String FILE_NON_EXIST = "文件不存在";

    @Resource
    UploadFileRecordsDao uploadFileRecordsDao;

    @Resource
    LogoCustomDao logoCustomDao;


    @ApiOperation(value = "谷歌安装包下载")
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "文件记录ID")
    })
    @RequestMapping(value = "/file/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String download(HttpServletResponse response) {

        String returnValue = DOWNLOAD_FAILURE;

        org.springframework.core.io.Resource resource = new ClassPathResource(FILE_URL);
        if (!resource.exists()) {
            return FILE_NON_EXIST;
        }

        //实现文件下载
        try (InputStream fis = resource.getInputStream()) {
            //配置文件下载
            response.setHeader("content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            //下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), "UTF-8"));
            OutputStream os = response.getOutputStream();
            IoUtil.copy(fis, os);
            os.flush();
            os.close();
            returnValue = DOWNLOAD_SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (DOWNLOAD_FAILURE.equals(returnValue)) {
            response.reset();
            ResultUtil.responseJson(response, ApiResponse.buildError("500", "下载失败!"));
        }
        return returnValue;
    }


    @ApiOperation(value = "图片回显")
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "图片记录ID")
    })
    @RequestMapping(value = "/file/showPicture", method = RequestMethod.GET)
    public ApiResponse<ResponseLogoCustom> showPicture(HttpServletResponse response) {

        ApiResponse<ResponseLogoCustom> returnValue = ApiResponse.buildError(ApiErrorEnum.E00001, "");

        LogoCustom data = logoCustomDao.showPicture();
        if (Objects.nonNull(data)) {

            ResponseLogoCustom var = new ResponseLogoCustom();

            if (Objects.nonNull(data.getTemporaryImage())) {
                var.setTemporaryImage(Base64.encode(data.getTemporaryImage()));
            }
            if (Objects.nonNull(data.getRealImage())) {
                var.setRealImage(Base64.encode(data.getRealImage()));
            }
            returnValue = ApiResponse.buildSuccess(var);
        }

  /*      try {
            //获取数据源
            LogoCustom data = logoCustomDao.showPicture();
            if (Objects.nonNull(data)) {
                //设置   图片回显
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                response.getOutputStream().write(data.getImage());
                response.flushBuffer();
            } else {
                ResultUtil.responseJson(response, ApiResponse.buildError("500", "图片下载失败!"));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.reset();
            ResultUtil.responseJson(response, ApiResponse.buildError("500", "图片下载失败!"));
        }*/
        return returnValue;
    }

    //点击确认以后临时logo存储实际logo
    @ApiOperation(value = "logoBase64编码")
    @RequestMapping(value = "/file/logoStorage", method = RequestMethod.GET)
    public ApiResponse<Boolean> showPicture() {
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.S00001, "");

        LogoCustom var = logoCustomDao.showPicture();
        if (Objects.nonNull(var)) {

            if (Objects.nonNull(var.getTemporaryImage())) {
                var.setRealImage(null);
                var.setRealImage(var.getTemporaryImage());
                var.setModifiedDate(LocalDateTime.now());
                logoCustomDao.save(var);
            }

            returnValue = ApiResponse.buildSuccess(true);
        }
        return returnValue;
    }

    @ApiOperation(value = "logo恢复默认")
    @RequestMapping(value = "/file/defaultLogo", method = RequestMethod.DELETE)
    public ApiResponse<Boolean> defaultLogo() {
        ApiResponse<Boolean> returnValue = ApiResponse.buildError(ApiErrorEnum.E00004, "");
        try {
            logoCustomDao.deleteAll();
            returnValue = ApiResponse.buildSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return returnValue;
    }


//    @ApiOperation(value = "Google安装程序下载")
//    @ApiImplicitParams({})
//    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public String download() {
//
//        String returnValue = DOWNLOAD_FAILURE;
//
//        URL url = Optional.ofNullable(FileDownloadController.class.getResource(FILE_URL)).orElse(null);
//        log.info("file url-------------------------------------------------->"+url);
//
//
//        if (Objects.isNull(url)) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return FILE_NON_EXIST;
//        }
//
//        File file = new File(url.getPath());
//        if (!file.exists()) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return FILE_NON_EXIST;
//        }
//
//        //实现文件下载
//        FileInputStream fis = null;
//        try {
//            //配置文件下载
//            response.setHeader("content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//            response.setContentLengthLong(file.length());
//            //下载文件能正常显示中文
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
//            fis = new FileInputStream(file);
//            OutputStream os = response.getOutputStream();
//            IOUtils.copy(fis, os);
//            os.flush();
//            os.close();
//            returnValue = DOWNLOAD_SUCCESS;
//        } catch (Exception e) {
//            log.error(e);
//        } finally {
//            if (Objects.nonNull(fis)) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (DOWNLOAD_FAILURE.equals(returnValue)) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//        return returnValue;
//    }


/*
    @ApiOperation(value = "Google安装程序下载")
    @ApiImplicitParams({})
    @RequestMapping(value = "/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String download() {

        String returnValue = DOWNLOAD_FAILURE;

        URL url = Optional.ofNullable(FileDownloadController.class.getResource(FILE_URL)).orElse(null);

        if (Objects.isNull(url)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return FILE_NON_EXIST;
        }

        File file = new File(url.getPath());
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return FILE_NON_EXIST;
        }

        //实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            //配置文件下载
            response.setHeader("content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentLengthLong(file.length());
            //下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            //response.setHeader("responseType", "arraybuffer");
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }

//            int i;
//            while ((i = bis.read(buffer)) != -1) {
//                os.write(buffer, 0, i);
//            }


            os.flush();
            os.close();
            returnValue = DOWNLOAD_SUCCESS;
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (Objects.nonNull(bis)) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(fis)) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (DOWNLOAD_FAILURE.equals(returnValue)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return returnValue;
    }
*/


}
