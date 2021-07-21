package com.example.docanalyzer.service;

import com.example.docanalyzer.dao.DocumentRepository;
import com.example.docanalyzer.models.DataPoint;
import com.example.docanalyzer.models.Document;
import lombok.Getter;
import lombok.Setter;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class DocumentService {

    @Value("${file.path.my.uniq}")
    private String path;

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document getDocumentsById(Long id){
        Document document = documentRepository.findById(id).isPresent() ? documentRepository.findById(id).get() : new Document("not found");
        List<DataPoint> resultPoints  = document.getDataPoints();
        resultPoints.sort(Comparator.comparing(DataPoint::getId));
        document.setDataPoints(resultPoints);
        return document;
    }

    public String saveDocument(MultipartFile file){
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            try {
                File buffer = new File(path + "/" + file.getOriginalFilename());
                Document documents = new Document(file.getOriginalFilename());

                file.transferTo(buffer);
                List<DataPoint> dataPoints = parseDocument(buffer);

                if(buffer.exists()){
                    buffer.delete();
                }

                if(!dataPoints.isEmpty()){
                    documents.setDataPoints(dataPoints);
                }

                documentRepository.save(documents);

                return "ok";
            } catch (IOException | TikaException e) {

                e.printStackTrace();
                return "err";
            }
        }

        return "file not exists";
    }

    private List<DataPoint> parseDocument(File file) throws IOException, TikaException {
        String result = new Tika().parseToString(file);
        List<DataPoint> dataPoints = new ArrayList<>();

        List<String> chapterString = Arrays.stream(result.split("\n{2,4}"))
                .map(x->x.trim())
                .filter(x->!x.isEmpty() && !x.contains("TS"))
                .collect(Collectors.toList());

        for (String chapter : chapterString) {

            DataPoint dataPoint = new DataPoint();
            String[] strings = chapter.split("([:.])\\s{2}");

            if(strings.length != 1){
                dataPoint.setTitle(strings[0]);
                dataPoint.setText(chapter.replace(strings[0],""));
            } else {
                dataPoint.setText(chapter);
            }

            dataPoints.add(dataPoint);
        }

        return dataPoints;
    }
}
