package com.kazma233.blog.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 下载
 * Created by mac_zly on 16/8/13.
 */

@Controller
@RequestMapping("/download")
public class DownloadController {

    private static final String DOWNLOAD_ROOT_DIR = "/download/";

    /**
     * 下载
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void getFile(@RequestParam("fileName") String fileName, HttpServletRequest request,
                        HttpServletResponse response) throws UnsupportedEncodingException {

        // 下载路径
        String downloadUrl = DOWNLOAD_ROOT_DIR + fileName;

        File file = new File(downloadUrl);
        if (!file.exists()) {
            return;
        }

        // 请求头
        request.setCharacterEncoding("UTF-8");
        // 设置一系列响应头MIME
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/x-msdownload");

        try (FileInputStream fileInputStream = new FileInputStream(downloadUrl);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

            // 转换成缓冲流
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());

            // 缓冲大小
            byte[] buff = new byte[1024];
            // 读取的长度
            int readLength;
            // 先读取到缓冲区上,再写出
            while ((readLength = bufferedInputStream.read(buff, 0, buff.length)) != -1) {
                bufferedOutputStream.write(buff, 0, readLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用于下载简历
    @RequestMapping("/getResume")
    public ResponseEntity<byte[]> springDownload() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "resume.doc");
        try {
            File file = new File("/kazma233/resume.doc");
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
