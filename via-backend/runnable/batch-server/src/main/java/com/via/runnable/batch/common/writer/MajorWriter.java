package com.via.runnable.batch.common.writer;

import com.via.common.app.career.dto.CreateMajor;
import com.via.common.app.career.service.MajorUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
@Slf4j
public class MajorWriter implements ItemWriter<CreateMajor> {
    private final MajorUseCase majorUseCase;

    @Value("#{stepExecutionContext['partitionNumber']}") Integer partitionNumber;

    @Override
    public void write(Chunk<? extends CreateMajor> chunk) {
        List<CreateMajor> createMajors = chunk.getItems()
                .stream()
                .map(u -> (CreateMajor) u)
                .toList();
        majorUseCase.createAndSave(createMajors);
    }
}
