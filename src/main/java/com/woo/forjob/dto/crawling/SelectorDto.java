package com.woo.forjob.dto.crawling;

import com.woo.forjob.entity.parameter.Career;
import com.woo.forjob.entity.parameter.SubCategory;
import com.woo.forjob.entity.parameter.Region;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SelectorDto {

    private List<SubCategory> categories;
    private List<Region> regions;
    private List<Career> careers;

    @Builder
    public SelectorDto(List<SubCategory> categories, List<Region> regions, List<Career> careers) {
        this.categories = categories;
        this.regions = regions;
        this.careers = careers;
    }
}
