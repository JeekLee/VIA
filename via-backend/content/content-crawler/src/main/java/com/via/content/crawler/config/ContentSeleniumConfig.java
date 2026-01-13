package com.via.content.crawler.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentSeleniumConfig {
    @PostConstruct
    public void setupDriver() {
        WebDriverManager.chromedriver()
                .avoidResolutionCache()
                .setup();
    }

    @Bean
    public WebDriver contentWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments(
                "--user-agent=Mozilla/5.0 (X11; Linux x86_64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/122.0.0.0 Safari/537.36"
        );

        return new ChromeDriver(options);
    }
}
