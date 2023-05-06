package com.woo.forjob.util.crawling;

import com.woo.forjob.dto.crawling.Param;
import com.woo.forjob.entity.parameter.*;
import com.woo.forjob.repository.parameter.CareerRepository;
import com.woo.forjob.repository.parameter.MainCategoryRepository;
import com.woo.forjob.repository.parameter.SubCategoryRepository;
import com.woo.forjob.repository.parameter.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Component
public class Selenium {

    private ChromeOptions options;
    private WebDriver driver;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final RegionRepository regionRepository;
    private final CareerRepository careerRepository;

//    @PostConstruct
//    public void init() throws InterruptedException {
//        setInCruit();
//    }

    public void setSelenium() {
        WebDriverManager.chromedriver().arm64().setup();
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
//        options.addArguments("headless");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(options);
    }

    public void setInCruit() throws InterruptedException {
        setSelenium();

        driver.get(CrawlingWebSite.INCRUIT.getUrl());
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        // 채용정보 클릭
        driver.findElement(By.xpath("//*[@id=\"utilMenu\"]/div/ul[1]/li[1]/a")).click();
        // 직종, 직무 탭 클릭
        driver.findElement(By.xpath("//*[@id=\"dropFirstList1\"]")).click();

        List<WebElement> elements = driver.findElements(By.cssSelector("#occ1_div > li"));
        for(WebElement element : elements) {
            String tag = element.findElement(By.tagName("button")).getText();
            if(!tag.contains("전체")) {
                element.findElement(By.tagName("button")).click();
                String div_id = element.findElement(By.tagName("button")).getAttribute("id").split("_")[2];

                MainCategory mainCategory = MainCategory.builder()
                        .category(tag)
                        .build();
                mainCategoryRepository.save(mainCategory);

                List<WebElement> divisions = driver.findElement(By.id("occ2_ul_" + div_id)).findElements(By.tagName("li"));
                for(WebElement division : divisions) {
                    WebElement button = division.findElement(By.tagName("button"));
                    String item = (button.getText().contains("전체")) ? "occ1" : "occ2";
                    String name = button.getText();
                    String[] id_split = button.getAttribute("id").split("_");
                    String value = id_split[id_split.length-1];

                    SubCategory subCategory = SubCategory.builder()
                            .mainCategory(mainCategory)
                            .subCategory(division.getText())
                            .item(item)
                            .value(value)
                            .build();
                    subCategoryRepository.save(subCategory);
                }
                driver.findElement(By.xpath("//*[@id=\"incruit_contents\"]/div[1]/div/div/div/div[3]/div[1]/button[1]")).click();
            }
        }

        // 지역선택 탭 클릭
        driver.findElement(By.xpath("//*[@id=\"dropFirstList2\"]")).click();
        // 국내지역 탭 클릭
        driver.findElement(By.xpath("//*[@id=\"rgn1_list_149\"]")).click();
        // 지역 관련 정보 셋팅
        elements = driver.findElements(By.cssSelector("#rgn2_ul_149 > li"));

        for(WebElement element: elements) {
            String name = element.findElement(By.tagName("span")).getText();
            if(!name.contains("전국")) {
                String id = element.findElement(By.tagName("button")).getAttribute("id");
                String[] id_split = id.split("_");

                String item = id_split[0];
                String value = id_split[id_split.length-1];

                Region region = Region.builder()
                        .webSite(CrawlingWebSite.INCRUIT)
                        .name(name)
                        .item(item)
                        .value(value)
                        .build();

                regionRepository.save(region);
            }
        }

        // 상세검색 탭
        driver.findElement(By.xpath("//*[@id=\"dropFirstList3\"]")).click();
        // 경력 관련 정보 셋팅
        elements = driver.findElements(By.cssSelector("#crr_list > option"));

        for(WebElement element : elements) {
            String year = element.getText();
            if(!year.contains("선택")) {
                String[] tmp = element.getAttribute("value").split("=");
                String item = tmp[0].replaceAll("[!@#$%^&]", "");
                String value = tmp[1];

                Career career = Career.builder()
                        .year(year)
                        .webSite(CrawlingWebSite.INCRUIT)
                        .item(item)
                        .value(value)
                        .build();
                careerRepository.save(career);
            }
        }
        driver.quit();
    }

    public void getCruits(List<Param> params) throws IOException {
        String url = "https://job.incruit.com/jobdb_list/searchjob.asp?";

        for(Param param: params) {
            String str = param.getItem() + "=" + param.getValue() + "&";
            url += str;
        }

        Connection conn = Jsoup.connect(url);
        Document html = conn.get();

        log.info(html.toString());
        Elements elements = html.getElementsByClass("sqr_paging sqr_pg_mid");
        log.info(elements.toString());
        for(Element element:elements) {
            log.info(element.text());
        }
    }
}
