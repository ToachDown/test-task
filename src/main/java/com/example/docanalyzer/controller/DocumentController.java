package com.example.docanalyzer.controller;

import com.example.docanalyzer.models.Document;
import com.example.docanalyzer.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "data-points/add-to-dataset", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveDocument(@RequestParam("file") MultipartFile file){
        String message = documentService.saveDocument(file);
        return message;
    }

    @GetMapping("documents/{documentId}/data-points")
    public Document getRequestDocChapter(@PathVariable Long documentId){
        return documentService.getDocumentsById(documentId);
    }
}
