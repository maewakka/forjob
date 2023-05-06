package com.woo.forjob.dto.crawling;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CrawlingRequestDto {
    private List<Long> categoryList;
    private List<Long> regionList;
    private List<String> queryList;

    @Builder
    public CrawlingRequestDto(List<Long> categoryList, List<Long> regionList, List<String> queryList) {
        this.categoryList = categoryList;
        this.regionList = regionList;
        this.queryList = queryList;
    }
}
