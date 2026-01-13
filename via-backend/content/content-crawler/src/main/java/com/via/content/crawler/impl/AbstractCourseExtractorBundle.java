package com.via.content.crawler.impl;

import com.via.content.crawler.extractor.*;
import com.via.content.domain.course.enums.CourseDifficulty;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public abstract class AbstractCourseExtractorBundle implements CourseExtractorBundle {

    private final URIExtractor uriExtractor;
    private final ImageUrlExtractor imageUrlExtractor;
    private final TitleExtractor titleExtractor;
    private final InstructorExtractor instructorExtractor;
    private final DescriptionExtractor descriptionExtractor;
    private final DifficultyExtractor difficultyExtractor;
    private final RatingExtractor ratingExtractor;
    private final SkillNamesExtractor skillNamesExtractor;

    protected AbstractCourseExtractorBundle(
            URIExtractor uriExtractor,
            ImageUrlExtractor imageUrlExtractor,
            TitleExtractor titleExtractor,
            InstructorExtractor instructorExtractor,
            DescriptionExtractor descriptionExtractor,
            DifficultyExtractor difficultyExtractor,
            RatingExtractor ratingExtractor,
            SkillNamesExtractor skillNamesExtractor
    ) {
        this.uriExtractor = Objects.requireNonNull(uriExtractor);
        this.imageUrlExtractor = Objects.requireNonNull(imageUrlExtractor);
        this.titleExtractor = Objects.requireNonNull(titleExtractor);
        this.instructorExtractor = Objects.requireNonNull(instructorExtractor);
        this.descriptionExtractor = Objects.requireNonNull(descriptionExtractor);
        this.difficultyExtractor = Objects.requireNonNull(difficultyExtractor);
        this.ratingExtractor = Objects.requireNonNull(ratingExtractor);
        this.skillNamesExtractor = Objects.requireNonNull(skillNamesExtractor);
    }

    @Override
    public URI extractURI(Element element) {
        return uriExtractor.extractFromElement(element);
    }

    @Override
    public String extractImageUrl(Element element) {
        return imageUrlExtractor.extractFromElement(element);
    }

    @Override
    public String extractTitle(Element element) {
        return titleExtractor.extractFromElement(element);
    }

    @Override
    public String extractInstructor(Element element) {
        return instructorExtractor.extractFromElement(element);
    }

    @Override
    public String extractDescription(Element element) {
        return descriptionExtractor.extractFromElement(element);
    }

    @Override
    public CourseDifficulty extractCourseDifficulty(Element element) {
        return difficultyExtractor.extractFromElement(element);
    }

    @Override
    public Float extractRating(Element element) {
        return ratingExtractor.extractFromElement(element);
    }

    @Override
    public List<String> extractSkillNames(Element element) {
        return skillNamesExtractor.extractFromElement(element);
    }
}
