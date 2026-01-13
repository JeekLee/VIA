package com.via.runnable.batch.common.partitioner;

import com.via.common.crawler.CorporationCrawler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CorporationPartitionerTest {
    private CorporationPartitioner corporationPartitioner;

    @Mock
    private CorporationCrawler corporationCrawler;

    @BeforeEach
    void setUp() {
        corporationPartitioner = new CorporationPartitioner(corporationCrawler);
    }

    @Test
    void partition_by_total_count() {
        // given
        Mockito.when(corporationCrawler.getTotalCount()).thenReturn(100000);

        // when
        Map<String, ExecutionContext> partition = corporationPartitioner.partition(5);

        // then
        ExecutionContext partition0 = partition.get("partition0");
        assertThat(partition0.getInt("startPage")).isEqualTo(0);
        assertThat(partition0.getInt("endPage")).isEqualTo(20);

        ExecutionContext partition1 = partition.get("partition1");
        assertThat(partition1.getInt("startPage")).isEqualTo(20);
        assertThat(partition1.getInt("endPage")).isEqualTo(40);
    }

}