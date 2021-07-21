package com.example.docanalyzer.dao;

import com.example.docanalyzer.models.Document;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface DocumentRepository extends Neo4jRepository<Document, Long> {
}

