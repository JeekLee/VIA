package com.via.common.infra.career.search.repository;

import com.via.common.infra.career.search.document.MajorDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MajorDocumentRepository extends ElasticsearchRepository<MajorDocument, Long> {
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
    Page<MajorDocument> findByName(String name, Pageable pageable);
}
