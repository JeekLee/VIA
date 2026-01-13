package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.ImageUrlExtractor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public final class InflearnImageUrlExtractor implements ImageUrlExtractor {

    @Override
    public String extractFromElement(Element div) {
        Element imgElement = div.selectFirst("img");
        if (imgElement == null) return null;

        String src = imgElement.attr("src");
        return !src.isEmpty() ? src : null;    }
}
