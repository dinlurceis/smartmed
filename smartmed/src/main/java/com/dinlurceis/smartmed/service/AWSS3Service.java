package com.dinlurceis.smartmed.service;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {
    String uploadFile(MultipartFile file, String folder);

    void deleteFile(String imageUrl);
}
