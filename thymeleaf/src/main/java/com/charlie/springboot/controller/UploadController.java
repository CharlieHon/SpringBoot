package com.charlie.springboot.controller;

import com.charlie.springboot.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.util.calendar.BaseCalendar;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
@Slf4j
public class UploadController {

    // 处理转发到用户注册，可以完成用户上传页面
    @GetMapping("/upload.html")
    public String uploadPage() {
        return "upload";    // 使用视图解析，转发到 template/upload.html
    }

    // 处理用户的注册请求，包括处理文件上传
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("name") String name, @RequestParam("email") String email,
                         @RequestParam("age") String age, @RequestParam("job") String job,
                         @RequestParam("header") MultipartFile header,
                         @RequestParam("photos") MultipartFile[] photos) throws IOException {

        // 输出获取到的信息
        log.info("上传的信息 name={} email={} age={} job={} header={} photos={}", name, email, age, job, header, photos);
        // 如果信息都成功得到，就将信息文件保存到指定目录
        // 1. 先将文件保存到指定目录
        // 2. 后面再吧温江保存到动态创建的目录

        // 1) 得到运行时的类路径
        String path = ResourceUtils.getURL("classpath:").getPath();
        // path=/E:/springboot/thymeleaf/target/classes/
        //log.info("path={}", path);
        File file = new File(path + WebUtils.getUploadFileDirectory());
        // 2) 动态创建指定目录
        if (!file.exists()) {   // 如果目录不存在，就创建
            file.mkdirs();
        }
        // 3) 将文件保存到指定目录/动态创建目录
        if (!header.isEmpty()) {    // 处理头像
            String originalFilename = header.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + originalFilename;
            // 这里需要指定保存文件的绝对路径
            //header.transferTo(new File("F:\\myshare\\" + originalFilename));
            // 保存文件的绝对路径=E:\springboot\thymeleaf\target\classes\static\images\ upload
            //log.info("保存文件的绝对路径={}", file.getAbsolutePath());
            header.transferTo(new File(file.getAbsolutePath() + "/" + fileName));
        }
        // 处理宠物图片
        if (photos.length > 0) {
            for (MultipartFile photo : photos) {    // 遍历
                if (!photo.isEmpty()) {
                    String originalFilename = photo.getOriginalFilename();
                    String fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + originalFilename;
                    //photo.transferTo(new File("F:\\myshare\\" + originalFilename));
                    photo.transferTo(new File(file.getAbsolutePath() + "/" + fileName));
                }
            }
        }

        return "注册用户成功/文件上传成功！";
    }
}
