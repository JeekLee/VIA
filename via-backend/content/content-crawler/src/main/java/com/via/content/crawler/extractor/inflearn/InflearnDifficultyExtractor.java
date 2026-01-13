package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.DifficultyExtractor;
import com.via.content.domain.course.enums.CourseDifficulty;
import com.via.core.error.ExceptionCreator;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import static com.via.content.crawler.exception.InflearnCrawlerException.FAILED_TO_PARSE_DIFFICULTY;

@Component
public final class InflearnDifficultyExtractor implements DifficultyExtractor {

    @Override
    public CourseDifficulty extractFromElement(Element div) {
        if (div == null) throw ExceptionCreator.create(FAILED_TO_PARSE_DIFFICULTY, "div: null");
        Element levelElement = div.selectFirst("p:nth-child(2)");
        if (levelElement == null) throw ExceptionCreator.create(FAILED_TO_PARSE_DIFFICULTY, "levelElement: null");
        String level = levelElement.text();
        // Korean difficulty levels from Inflearn website
        if (level.contains("입문")) return CourseDifficulty.BEGINNER;
        if (level.contains("초급")) return CourseDifficulty.ELEMENTARY;
        if (level.contains("중급")) return CourseDifficulty.INTERMEDIATE;
        if (level.contains("고급")) return CourseDifficulty.ADVANCED;
        throw  ExceptionCreator.create(FAILED_TO_PARSE_DIFFICULTY, "level: " + level);
    }
}
