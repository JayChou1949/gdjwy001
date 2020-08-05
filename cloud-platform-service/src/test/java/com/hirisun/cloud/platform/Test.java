package com.hirisun.cloud.platform;

import com.hirisun.cloud.api.file.FileApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhoufeng
 * @version 1.0
 * @className Test
 * @data 2020/8/5 13:59
 * @description
 */
@SpringBootTest(classes = PlatformApplication.class)
@Slf4j
public class Test {

    @Autowired
    private FileApi fileApi;

    @org.junit.jupiter.api.Test
    @SneakyThrows
    public void testHandleFileUpload() {

        File file = new File("C:\\Users\\mfkge\\Desktop\\1.jpg");
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file",
                MediaType.TEXT_PLAIN_VALUE, true, file.getName());

        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        MultipartFile multi = new CommonsMultipartFile(fileItem);
        String fileId = fileApi.upload(multi, "test", "test");
        log.debug("<== fileId:{}",fileId);
    }
}
