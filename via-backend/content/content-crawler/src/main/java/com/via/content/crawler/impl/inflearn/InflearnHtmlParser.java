package com.via.content.crawler.impl.inflearn;

import com.via.content.crawler.dto.CrawledCourse;
import com.via.content.domain.course.enums.CourseDifficulty;
import com.via.core.error.ExceptionCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.via.content.crawler.exception.InflearnCrawlerException.*;
import static com.via.content.domain.course.enums.CoursePlatform.INFLEARN;

@Component
@Slf4j
@RequiredArgsConstructor
public class InflearnHtmlParser {

    private final InflearnExtractorBundle extractorBundle;

    public List<CrawledCourse> parse(String html) {
        List<CrawledCourse> result = new ArrayList<>();
        Elements courseElements = getCourseElements(html);
        for (Element courseElement : courseElements) result.add(parseCourse(courseElement));
        return result;
    }

    private Elements getCourseElements(String html) {
        Document doc = Jsoup.parse(html);
        Elements courseElements = doc.select("ul > li");

        if (courseElements.isEmpty()) throw ExceptionCreator.create(COURSE_LIST_NOT_FOUND);

        return courseElements;
    }

    private CrawledCourse parseCourse(Element li) {
        Element hrefElement = li.selectFirst("a[href]");
        URI url = extractorBundle.extractURI(hrefElement);

        Element article = li.selectFirst("article");
        if (article == null) throw ExceptionCreator.create(ARTICLE_NOT_FOUND);

        // Extract image
        Element imageSourceDiv = article.selectFirst("> div:nth-child(1)");
        if (imageSourceDiv == null) throw ExceptionCreator.create(META_DIV_NOT_FOUND);
        String imageUrl = extractorBundle.extractImageUrl(imageSourceDiv);


        Element courseMetaDiv = article.selectFirst("> div:nth-child(2)");
        if (courseMetaDiv == null) throw ExceptionCreator.create(META_DIV_NOT_FOUND);

        Element firstDiv = courseMetaDiv.selectFirst("> div:nth-child(1)");
        String title = extractorBundle.extractTitle(firstDiv);
        String instructor = extractorBundle.extractInstructor(firstDiv);

        Element secondDiv = courseMetaDiv.selectFirst("> div:nth-child(2)");
        Float rating = extractorBundle.extractRating(secondDiv);

        Element thirdDiv = courseMetaDiv.selectFirst("> div:nth-child(3)");
        String description = extractorBundle.extractDescription(thirdDiv);
        CourseDifficulty difficulty = extractorBundle.extractCourseDifficulty(thirdDiv);
        List<String> skillsAsString = extractorBundle.extractSkillNames(thirdDiv);

        return new CrawledCourse(
                url, INFLEARN, imageUrl,
                title, instructor, description,
                difficulty, rating, skillsAsString
        );
    }
}
