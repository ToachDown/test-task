package com.example.docanalyzer.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@Node
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    private String documentName;

    @Relationship(type = "INCLUDES",direction = Relationship.Direction.OUTGOING)
    private List<DataPoint> dataPoints = new ArrayList<>();

    public Document() {
    }

    public Document(String documentName) {
        this.documentName = documentName;
    }
}
