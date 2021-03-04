package com.free.badmood.blackhole.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
@RestController
public class PhotoFileUploadController {

    @Value("${upload-res-photo-dir}")
    private String uploadResPhotoDir;


    @Value("${project-url}")
    private String projectUrl;


    @RequestMapping("/uploadPhotos")
    public String uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = "";
        request.setCharacterEncoding("utf-8"); //设置编码
//        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
        String realPath = uploadResPhotoDir;
        File dir = new File(realPath);
        //文件目录不存在，就创建一个
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            //获取formdata的值
            Iterator<String> iterator = req.getFileNames();
            String articleId = request.getParameter("articleId");
            log.error("文章id：" + articleId);
            String userId = request.getParameter("userId");
            log.error("用户id：" + userId);
//            String timedata = request.getParameter("timedata");

            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String fileName = file.getOriginalFilename();
                //真正写到磁盘上
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String kzm = fileName.substring(fileName.lastIndexOf("."));
                String filename = uuid + kzm;
                File file1 = new File(realPath + filename);
                log.error("上传文件的绝对路径：" + file1.getAbsolutePath());
                OutputStream out = new FileOutputStream(file1);
                out.write(file.getBytes());
                out.close();
                filePath = projectUrl
                        + request.getContextPath() + "/" + filename;
                System.out.println("访问图片路径:" + filePath );
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return filePath;

    }
}
