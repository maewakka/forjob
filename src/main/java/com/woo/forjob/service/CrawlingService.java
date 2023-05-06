package com.woo.forjob.service;

import com.woo.forjob.dto.crawling.*;
import com.woo.forjob.entity.parameter.MainCategory;
import com.woo.forjob.entity.recruit.RecruitPost;
import com.woo.forjob.entity.parameter.Region;
import com.woo.forjob.entity.parameter.SubCategory;
import com.woo.forjob.repository.recruit.*;
import com.woo.forjob.repository.parameter.CareerRepository;
import com.woo.forjob.repository.parameter.MainCategoryRepository;
import com.woo.forjob.repository.parameter.RegionRepository;
import com.woo.forjob.repository.parameter.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CrawlingService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CareerRepository careerRepository;
    private final RegionRepository regionRepository;
    private final RecruitPostRepository recruitPostRepository;

    public List<MainCategoryDto> getMainCategory() {
        List<MainCategory> categories = mainCategoryRepository.findAll();
        List<MainCategoryDto> requestDto = new ArrayList<>();
        for(MainCategory category : categories) {
            requestDto.add(MainCategoryDto.toDto(category));
        }
        return requestDto;
    }

    public List<SubCategoryDto> getSubCategory(Long mainCategoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).orElseThrow(() -> new EntityNotFoundException());
        List<SubCategory> categories = subCategoryRepository.findAllByMainCategory(mainCategory);
        List<SubCategoryDto> requestDto = new ArrayList<>();
        for(SubCategory category : categories) {
            requestDto.add(SubCategoryDto.toDto(category));
        }
        return requestDto;
    }

    public List<RegionDto> getRegion() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDto> requestDto = new ArrayList<>();
        for(Region region : regions) {
            requestDto.add(RegionDto.toDto(region));
        }
        return requestDto;
    }

    public void setInform(CrawlingRequestDto requestDto) throws IOException {
        List<String> urlList = this.createUrls(requestDto);

        for(String url:urlList) {
            Connection conn = Jsoup.connect(url);
            Document html = conn.get();

            if(!html.getElementsByTag("ul").hasClass("c_row empty")) {
                Integer pageNum = 1;

                if(html.getElementsByTag("a").hasClass("f_next_n")) {
                    String str = html.getElementsByClass("f_next_n").attr("href");
                    String[] strs = str.split("&");
                    pageNum = Integer.parseInt(strs[strs.length-1].split("=")[1]);
                } else if(html.getElementsByTag("a").hasClass("next_n")) {
                    Elements elements = html.getElementsByClass("sqr_paging sqr_pg_mid");
                    pageNum = elements.get(0).getElementsByTag("a").size();
                }

                for(int i=1;i<pageNum+1;i++) {
                    String pageUrl = url + "page=" + Integer.toString(i);
                    conn = Jsoup.connect(pageUrl);
                    html = conn.get();

                    Elements elements = html.getElementsByClass("c_row");
                    for(Element element:elements) {
                        String company = element.getElementsByClass("cell_first").get(0).getElementsByTag("a").text().trim();
                        String title = element.getElementsByClass("cell_mid").get(0).getElementsByTag("a").text().trim();
                        List<String> spans = element.getElementsByClass("cell_mid").get(0).getElementsByTag("span").eachText();

                        String career = spans.get(0).trim();
                        String education = spans.get(1).trim();
                        String location = spans.get(2).trim();
                        String workType = spans.get(3).trim();

                        spans = element.getElementsByClass("cell_last").get(0).getElementsByTag("span").eachText();
                        String deadline = spans.get(0).trim();
                        String lastModified = spans.get(1).trim();

                        RecruitPost recruitPost = RecruitPost.builder()
                                .company(company)
                                .title(title)
                                .career(career)
                                .education(education)
                                .location(location)
                                .workType(workType)
                                .deadline(deadline)
                                .lastModified(lastModified)
                                .build();

                        if(!recruitPostRepository.existsByTitle(title)) {
                            recruitPostRepository.save(recruitPost);
                        }
                    }
                }
            }
        }
    }

    public List<String> createUrls(CrawlingRequestDto requestDto) {
        List<Param> params = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        String url = "https://job.incruit.com/jobdb_list/searchjob.asp?";

        // 지역정보와 카테고리들을 반영한 URL
        for(Long id:requestDto.getCategoryList()) {
            SubCategory category = subCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
            params.add(Param.builder()
                    .item(category.getItem())
                    .value(category.getValue())
                    .build());
        }
        for(Long id: requestDto.getRegionList()) {
            Region region = regionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
            params.add(Param.builder()
                    .item(region.getItem())
                    .value(region.getValue())
                    .build());
        }
        for(Param param: params) {
            String str = param.getItem() + "=" + param.getValue() + "&";
            url += str;
        }
        urlList.add(url);

        // 직접 입력한 Query를 위한 URL
        if(!requestDto.getQueryList().isEmpty()) {
            for (String query : requestDto.getQueryList()) {
                url = "https://job.incruit.com/jobdb_list/searchjob.asp?";
                for (Param param : params) {
                    String str = param.getItem() + "=" + param.getValue() + "&";
                    url += str;
                }
                url += "kw=" + query;
                urlList.add(url);
            }
        }
        return urlList;
    }
}
