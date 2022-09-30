package com.syntechsia.helper.service.syntechsiahelperservice.controller;

import com.syntechsia.helper.service.syntechsiahelperservice.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(path = "/import-excel")
    public void importTransactionsFromExcelToDb(@RequestPart List<MultipartFile> files) {
        documentService.importExcelDocument(files);
    }

}
