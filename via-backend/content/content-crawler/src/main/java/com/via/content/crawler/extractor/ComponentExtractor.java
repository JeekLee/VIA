package com.via.content.crawler.extractor;

import org.jsoup.nodes.Element;

public interface ComponentExtractor<T> {
    T extractFromElement(Element element);
}
