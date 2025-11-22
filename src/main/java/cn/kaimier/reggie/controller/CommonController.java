package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @GetMapping("/download")
    public String download(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "文件下载功能待实现";
    }

    /**
     * 文件上传接口
     * @param file 上传的文件
     * @return 返回上传后的文件名
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀名（包含点号，如 .jpg）
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID生成唯一文件名，避免文件名冲突
        String fileName = UUID.randomUUID().toString() + suffix;
        // 创建文件存储目录对象
        File dir = new File(basePath);
        // 如果目录不存在，则创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            // 将上传的文件保存到指定路径
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            // 文件保存失败时抛出运行时异常
            throw new RuntimeException(e);
        }
        // 返回成功响应，包含生成的文件名
        return R.success(fileName);
    }
}
