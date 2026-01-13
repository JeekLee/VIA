package com.via.runnable.batch.common.processor;

import com.via.common.app.career.dto.CreateUniversity;
import com.via.common.crawler.dto.CrawledUniversity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUniversityProcessor implements ItemProcessor<CrawledUniversity, CreateUniversity> {
    @Override
    public CreateUniversity process(CrawledUniversity crawledUniversity) {
        return new CreateUniversity(crawledUniversity.code(), crawledUniversity.name());
    }
}
