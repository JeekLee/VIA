package com.via.runnable.batch.common.reader;

import com.via.common.crawler.CorporationCrawler;
import com.via.common.crawler.dto.CrawledCorporation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CorporationClientReaderTest {
    private CorporationClientReader reader;

    @Mock
    private CorporationCrawler corporationCrawler;

    private static final int PARTITION_NUMBER = 0;
    private static final int START_PAGE = 0;
    private static final int END_PAGE = 3;
    private static final int PAGE_SIZE = 50;

    @BeforeEach
    void setUp() {
        reader = new CorporationClientReader(corporationCrawler);
    }

    private ExecutionContext creatExecutionContext() {
        ExecutionContext executionContext = new ExecutionContext();
        executionContext.put("partitionNumber", PARTITION_NUMBER);
        executionContext.put("startPage", START_PAGE);
        executionContext.put("endPage", END_PAGE);
        executionContext.put("pageSize", PAGE_SIZE);
        return executionContext;
    }

    private void injectStepExecutionContextValues(ExecutionContext context) {
        setField(reader, "partitionNumber", context.getInt("partitionNumber"));
        setField(reader, "startPage", context.getInt("startPage"));
        setField(reader, "endPage", context.getInt("endPage"));
        setField(reader, "pageSize", context.getInt("pageSize"));
    }

    private List<CrawledCorporation> createMockCorporations(int count) {
        List<CrawledCorporation> crawledCorporations = new ArrayList<>();
        for (int i = 0; i < count; i++) crawledCorporations.add(mock(CrawledCorporation.class));
        return crawledCorporations;
    }

    @Test
    void read_corporations_page_by_page() throws Exception {
        // given
        ExecutionContext executionContext = creatExecutionContext();
        injectStepExecutionContextValues(executionContext);

        List<CrawledCorporation> page1Data = createMockCorporations(50);
        List<CrawledCorporation> page2Data = createMockCorporations(25);

        Mockito.when(corporationCrawler.findAll(1, PAGE_SIZE)).thenReturn(page1Data);
        Mockito.when(corporationCrawler.findAll(2, PAGE_SIZE)).thenReturn(page2Data);

        // when
        reader.open(executionContext);

        // then
        for (int i = 0; i < 50; i++) {
            CrawledCorporation crawledCorporation = reader.read();
            assertThat(crawledCorporation).isNotNull();
        }

        for (int i = 0; i < 25; i++) {
            CrawledCorporation crawledCorporation = reader.read();
            assertThat(crawledCorporation).isNotNull();
        }

        assertThat(reader.read()).isNull();
    }
}