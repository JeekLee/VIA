package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.TitleExtractor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public final class InflearnTitleExtractor implements TitleExtractor {

    @Override
    public String extractFromElement(Element div) {
        if (div == null) return null;
        Element titleElement = div.selectFirst("> p:nth-child(1)");
        return (titleElement != null) ? titleElement.text() : null;
    }
}
