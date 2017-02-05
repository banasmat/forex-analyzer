package com.jorm.forex.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {

    // TODO consider moving from utils

    public static File convertMultipartFileToTempFile(MultipartFile multipartFile, String targetDir) throws IOException {
        File file = new File(targetDir + multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }
}
