package com.example.docanalyzer.models;

import org.springframework.data.neo4j.core.schema.Node;

@Node
public class DataPoint extends Document{


    private String title;

    private String text;

    public DataPoint(){

    }

    public DataPoint(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
