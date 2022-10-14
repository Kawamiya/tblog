package com.tblog.blog_api.controller;

import com.tblog.blog_api.utils.QiniuUtils;
import com.tblog.blog_api.vo.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;
    @PostMapping
    public com.blog_api.vo.Result uploadPicture(@RequestParam("image") MultipartFile file){
        //获得原始文件名称 比如TB.png
        String originalFilename = file.getOriginalFilename();
        //唯一的文件名称  StringUtils.substringAfterLast为分割方法，比如文件名为a.jpg，此方法会返回jpg
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename,".");
        //上传文件 上传到图片服务器

        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return com.blog_api.vo.Result.SuccessResponse(QiniuUtils.url + fileName);
        }
        return com.blog_api.vo.Result.FailedResponse(ErrorCode.FILE_UPLOAD_ERROR.getCode(),ErrorCode.FILE_UPLOAD_ERROR.getMsg());
    }
}
