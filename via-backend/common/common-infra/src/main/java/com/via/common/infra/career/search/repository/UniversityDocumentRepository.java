package com.via.common.infra.career.search.repository;

import com.via.common.infra.career.search.document.UniversityDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UniversityDocumentRepository extends ElasticsearchRepository<UniversityDocument, Long> {
    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "match": {
                      "name": {
                        "query": "?0",
                        "boost": 2.0
                      }
                    }
                  },
                  {
                    "match": {
                      "name.ngram": {
                        "query": "?0",
                        "boost": 1.0
                      }
                    }
                  }
                ]
              }
            }
            """)
    Page<UniversityDocument> findByName(String name, Pageable pageable);
}
