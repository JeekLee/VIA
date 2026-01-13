package com.via.content.infra.course.search.repository.custom.impl;

import com.via.content.infra.course.search.entity.CourseDocument;
import com.via.content.infra.course.search.repository.custom.CourseDocumentRepositoryCustom;
import com.via.core.error.ExceptionCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.via.content.infra.course.exception.CourseSearchException.QUERY_PROCESSING_FAILED;


@Repository
@RequiredArgsConstructor
@Slf4j
public class CourseDocumentRepositoryCustomImpl implements CourseDocumentRepositoryCustom {
    private final EmbeddingModel embeddingModel;
    private final ObjectMapper objectMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<CourseDocument> searchSimilarByQuery(String input) {
        float[] embeddings = embeddingModel.embed(input);

        Map<String, Object> queryMap = Map.of(
                "knn", Map.of(
                        "textEmbedding", Map.of(
                                "vector", embeddings,
                                "k", 10
                        )
                )
        );

        StringQuery query;
        try {
            query = new StringQuery(objectMapper.writeValueAsString(queryMap));
        } catch (JsonProcessingException e) {
            throw ExceptionCreator.create(QUERY_PROCESSING_FAILED, queryMap.toString());
        }

        SearchHits<CourseDocument> searchHits = elasticsearchOperations.search(
                query,
                CourseDocument.class
        );

        return searchHits.getSearchHits().stream()
                .peek(hit -> log.info("Score: {}, Content: {}", hit.getScore(), hit.getContent().getText()))
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
