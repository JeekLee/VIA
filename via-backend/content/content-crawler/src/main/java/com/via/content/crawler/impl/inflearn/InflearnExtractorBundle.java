package com.via.content.crawler.impl.inflearn;

import com.via.content.crawler.extractor.inflearn.*;
import com.via.content.crawler.impl.AbstractCourseExtractorBundle;
import org.springframework.stereotype.Component;

@Component
public class InflearnExtractorBundle extends AbstractCourseExtractorBundle {

    public InflearnExtractorBundle(
            InflearnURIExtractor uriExtractor,
            InflearnImageUrlExtractor imageUrlExtractor,
            InflearnTitleExtractor titleExtractor,
            InflearnInstructorExtractor instructorExtractor,
            InflearnDescriptionExtractor descriptionExtractor,
            InflearnDifficultyExtractor difficultyExtractor,
            InflearnRatingExtractor ratingExtractor,
            InflearnSkillNamesExtractor skillNamesExtractor
    ) {
        super(
                uriExtractor,
                imageUrlExtractor,
                titleExtractor,
                instructorExtractor,
                descriptionExtractor,
                difficultyExtractor,
                ratingExtractor,
                skillNamesExtractor
        );
    }
}
