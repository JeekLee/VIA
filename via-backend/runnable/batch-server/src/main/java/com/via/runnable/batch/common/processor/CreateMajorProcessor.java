package com.via.runnable.batch.common.processor;

import com.via.common.app.career.dto.CreateMajor;
import com.via.common.crawler.dto.CrawledMajor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMajorProcessor implements ItemProcessor<CrawledMajor, CreateMajor> {
    @Override
    public CreateMajor process(CrawledMajor crawledMajor) {
        return new CreateMajor(crawledMajor.code(), crawledMajor.name());
    }
}
