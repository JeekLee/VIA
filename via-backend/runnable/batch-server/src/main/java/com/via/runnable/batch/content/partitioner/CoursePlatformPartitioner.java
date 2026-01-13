package com.via.runnable.batch.content.partitioner;

import com.via.content.domain.course.enums.CoursePlatform;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CoursePlatformPartitioner implements Partitioner {

    @Override
    @NonNull
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = new HashMap<>();

        CoursePlatform[] platforms = CoursePlatform.values();

        for (CoursePlatform platform : platforms) {
            ExecutionContext context = new ExecutionContext();
            context.putString("platform", platform.name());
            String partitionKey = "partition-" + platform.name();
            partitions.put(partitionKey, context);
        }

        return partitions;
    }
}
