package com.via.runnable.batch.common.partitioner;

import com.via.common.crawler.UniversityCrawler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class UniversityPartitioner implements Partitioner {
    private final UniversityCrawler universityCrawler;
    private static final int PAGE_SIZE = 100;

    @Override
    @NonNull
    public Map<String, ExecutionContext> partition(int gridSize) {
        int totalCount = universityCrawler.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        log.info("University Pages: totalCount={}, totalPages={}", totalCount, totalPages);

        Map<String, ExecutionContext> partitions = new HashMap<>();
        int pagesPerPartition = (int) Math.ceil((double) totalPages / gridSize);

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();

            int startPage = i * pagesPerPartition;
            int endPage = Math.min(startPage + pagesPerPartition, totalPages);

            context.putInt("partitionNumber", i);
            context.putInt("startPage", startPage);
            context.putInt("endPage", endPage);
            context.putInt("pageSize", PAGE_SIZE);
            partitions.put("partition" + i, context);
        }

        return partitions;
    }
}
