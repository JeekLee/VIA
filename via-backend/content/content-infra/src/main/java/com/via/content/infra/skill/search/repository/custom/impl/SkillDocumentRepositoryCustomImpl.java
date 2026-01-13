package com.via.content.infra.skill.search.repository.custom.impl;

import com.via.content.infra.skill.search.entity.SkillDocument;
import com.via.content.infra.skill.search.repository.custom.SkillDocumentRepositoryCustom;
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

import java.util.Map;
import java.util.Optional;

import static com.via.content.infra.skill.exception.SkillSearchException.QUERY_PROCESSING_FAILED;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SkillDocumentRepositoryCustomImpl implements SkillDocumentRepositoryCustom {
    private final EmbeddingModel embeddingModel;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<SkillDocument> findTopBySimilarity(String input) {
        if (input == null || input.isBlank()) return Optional.empty();

        float[] embeddings;
        try{
            embeddings = embeddingModel.embed(input);
        }
        catch (Exception e) {
            log.info("Failed to embed input: {}", input, e);
            return Optional.empty();
        }


        Map<String, Object> queryMap = Map.of(
                "nested", Map.of(
                        "path", "aliasEmbeddings",
                        "score_mode", "max",
                        "query", Map.of(
                                "knn", Map.of(
                                        "aliasEmbeddings.vector", Map.of(
                                                "vector", embeddings,
                                                "k", 5
                                        )
                                )
                        )
                )
        );

        StringQuery query;
        try {
            query = new StringQuery(objectMapper.writeValueAsString(queryMap));
        } catch (JsonProcessingException e) {
            throw ExceptionCreator.create(QUERY_PROCESSING_FAILED, queryMap.toString());
        }

        SearchHits<SkillDocument> searchHits = elasticsearchOperations.search(
                query,
                SkillDocument.class
        );

        if (searchHits.getMaxScore() < 0.85f) {
            log.info("Input: {}, searchHits: {}", input, searchHits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .map(SkillDocument::getName)
                    .toList());
            return Optional.empty();
        }
        return searchHits.getSearchHits().stream().findFirst().map(SearchHit::getContent);
    }
}
