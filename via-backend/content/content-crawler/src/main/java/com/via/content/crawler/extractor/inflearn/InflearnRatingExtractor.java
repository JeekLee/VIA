package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.RatingExtractor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public final class InflearnRatingExtractor implements RatingExtractor {

    @Override
    public Float extractFromElement(Element div) {
        if (div == null) return null;
        Element ratingElement = div.selectFirst("svg.fa-star + p");
        return (ratingElement != null) ? Float.parseFloat(ratingElement.text()) : 0.0f;
    }
}
