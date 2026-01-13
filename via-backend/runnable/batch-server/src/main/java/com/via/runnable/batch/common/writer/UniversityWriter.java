package com.via.runnable.batch.common.writer;

import com.via.common.app.career.dto.CreateUniversity;
import com.via.common.app.career.service.UniversityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
@Slf4j
public class UniversityWriter implements ItemWriter<CreateUniversity> {
    private final UniversityUseCase universityUseCase;

    @Override
    public void write(Chunk<? extends CreateUniversity> chunk) {
        List<CreateUniversity> createUniversities = chunk.getItems()
                .stream()
                .map(u -> (CreateUniversity) u)
                .toList();

        universityUseCase.createAndSave(createUniversities);
    }
}
