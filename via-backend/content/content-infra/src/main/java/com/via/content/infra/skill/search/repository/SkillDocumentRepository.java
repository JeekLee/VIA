package com.via.content.infra.skill.search.repository;

import com.via.content.infra.skill.search.entity.SkillDocument;
import com.via.content.infra.skill.search.repository.custom.SkillDocumentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkillDocumentRepository extends ElasticsearchRepository<SkillDocument, Long>, SkillDocumentRepositoryCustom {
    @Query("""
        {
          "bool": {
            "must": [
              {
                "nested": {
                  "path": "aliasEmbeddings",
                  "score_mode": "max",
                  "query": {
                    "bool": {
                      "should": [
                        {"term": {"aliasEmbeddings.alias.keyword": {"value": "?0", "boost": 5.0}}},
                        {"match": {"aliasEmbeddings.alias": {"query": "?0", "operator": "and", "boost": 4.0}}},
                        {"match": {"aliasEmbeddings.alias.ngram": {"query": "?0", "boost": 2.0}}},
                        {"match": {"aliasEmbeddings.alias": {"query": "?0", "operator": "or", "boost": 1.0}}}
                      ]
                    }
                  }
                }
              }
            ]
          }
        }
        """)
    Page<SkillDocument> findByName(String name, Pageable pageable);
}
