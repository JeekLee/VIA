package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.InstructorExtractor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public final class InflearnInstructorExtractor implements InstructorExtractor {

    @Override
    public String extractFromElement(Element div) {
        if (div == null) return null;
        Element instructorElement = div.selectFirst("> p:nth-child(2)");
        return (instructorElement != null) ? instructorElement.text() : null;
    }
}
