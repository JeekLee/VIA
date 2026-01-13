package com.via.content.crawler.extractor.inflearn;

import com.via.content.crawler.extractor.SkillNamesExtractor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class InflearnSkillNamesExtractor implements SkillNamesExtractor {

    @Override
    public List<String> extractFromElement(Element div) {
        if (div == null) return null;
        Element tagsElement = div.selectFirst("p.css-ugmdna");
        if (tagsElement == null) return null;
        String tags = tagsElement.text();
        return List.of(tags.split(", "));
    }
}
