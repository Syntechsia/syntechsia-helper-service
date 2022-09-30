package com.syntechsia.helper.service.syntechsiahelperservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    public void importExcelDocument(List<MultipartFile> multipartFiles);
}
