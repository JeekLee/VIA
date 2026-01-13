package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.URIExtractor;
import com.via.core.error.ExceptionCreator;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.net.URI;

import static com.via.content.crawler.exception.InflearnCrawlerException.ARTICLE_NOT_FOUND;

@Component
public final class InflearnURIExtractor implements URIExtractor {

    @Override
    public URI extractFromElement(Element element) {
        if (element == null) throw ExceptionCreator.create(ARTICLE_NOT_FOUND);
        return URI.create(element.attr("href").split("\\?")[0]);
    }
}
