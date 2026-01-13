package com.via.content.crawler.impl.inflearn;

import com.via.content.crawler.CourseCrawler;
import com.via.content.crawler.dto.CrawledCourse;
import com.via.content.domain.course.enums.CoursePlatform;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.via.content.domain.course.enums.CoursePlatform.INFLEARN;

@Component
@Slf4j
@RequiredArgsConstructor
public class InflearnCourseCrawler implements CourseCrawler {
    private static final String INFLEARN_URL = "https://www.inflearn.com/courses";
    // Korean text for "Course List" section header on Inflearn website
    private static final String COURSE_LIST_HEADER = "강의 리스트";

    private final WebDriver driver;
    private final InflearnHtmlParser parser;
    private WebDriverWait wait;

    @PostConstruct
    public void setUpWait() {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Override
    public CoursePlatform getPlatform() {
        return INFLEARN;
    }

    @Override
    public List<CrawledCourse> crawl() {
        log.info("[{}] Crawling started", this.getPlatform());

        List<CrawledCourse> result = new ArrayList<>();

        driver.get(INFLEARN_URL);
        int currentPage = 1;

        while (true) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            String courseListSection = extractCourseListSection(javascriptExecutor);

            try {
                result.addAll(parser.parse(courseListSection));
            }
            catch (Exception e) {
                log.warn("[{}] Failed to parse page {}: {}", this.getPlatform(), currentPage, e.getMessage());
            }

            currentPage++;

            try {
                javascriptExecutor.executeScript(
                        "arguments[0].click();",
                        wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//button[contains(@class, 'mantine-Pagination-control') and text()='" + currentPage + "']")
                        ))
                );
            }
            catch (TimeoutException e) {
                log.info("[{}] Next page button {} not found.", this.getPlatform(), currentPage);
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.warn("Sleep interrupted, continuing...", e);
                Thread.currentThread().interrupt();
            }

        }

        log.info("[{}] Crawling completed", this.getPlatform());
        return result;
    }

    private String extractCourseListSection(JavascriptExecutor javascriptExecutor) {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//section[.//h2[contains(text(), '" + COURSE_LIST_HEADER + "')]]")
        ));

        return (String) javascriptExecutor.executeScript(
                "const section = document.evaluate(" +
                        "  \"//section[.//h2[contains(text(), '" + COURSE_LIST_HEADER + "')]]\", " +
                        "  document, " +
                        "  null, " +
                        "  XPathResult.FIRST_ORDERED_NODE_TYPE, " +
                        "  null" +
                        ").singleNodeValue; " +
                        "return section ? section.outerHTML : null;"
        );
    }
}
