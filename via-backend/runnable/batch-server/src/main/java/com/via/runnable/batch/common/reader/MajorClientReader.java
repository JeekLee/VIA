package com.via.runnable.batch.common.reader;

import com.via.common.crawler.MajorCrawler;
import com.via.common.crawler.dto.CrawledMajor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class MajorClientReader extends AbstractPagingItemReader<CrawledMajor> {
    private final MajorCrawler majorCrawler;

    @Value("#{stepExecutionContext['partitionNumber']}") Integer partitionNumber;
    @Value("#{stepExecutionContext['startPage']}") Integer startPage;
    @Value("#{stepExecutionContext['endPage']}") Integer endPage;
    @Value("#{stepExecutionContext['pageSize']}") Integer pageSize;

    private int currentPage;

    @Override
    protected void doOpen() throws Exception {
        setPageSize(pageSize);
        super.doOpen();
        currentPage = startPage;
        log.info("[Major Partition-{}]: pages={}-{} (Total {} pages)", partitionNumber, startPage, endPage - 1, endPage - startPage);
    }

    @Override
    protected void doReadPage() {
        if (results == null) results = new CopyOnWriteArrayList<>();
        else results.clear();

        if (currentPage >= endPage) return;

        List<CrawledMajor> majors = majorCrawler.findAll(currentPage + 1, getPageSize());
        if (majors != null && !majors.isEmpty()) results.addAll(majors);
        currentPage++;
    }
}
