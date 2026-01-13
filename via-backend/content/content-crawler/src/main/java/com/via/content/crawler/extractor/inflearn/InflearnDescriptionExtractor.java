package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.DescriptionExtractor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public final class InflearnDescriptionExtractor implements DescriptionExtractor {

    @Override
    public String extractFromElement(Element div) {
        if (div == null) return null;
        Element descriptionElement = div.selectFirst("p:nth-of-type(1)");
        return (descriptionElement != null) ? descriptionElement.text() : null;
    }
}
