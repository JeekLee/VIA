package com.via.runnable.batch.common.processor;

import com.via.common.app.career.dto.CreateCorporation;
import com.via.common.crawler.dto.CrawledCorporation;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCorporationProcessor implements ItemProcessor<CrawledCorporation, CreateCorporation> {
    @Override
    public CreateCorporation process(CrawledCorporation crawledCorporation) {
        return new CreateCorporation(crawledCorporation.code(), crawledCorporation.name());
    }
}
