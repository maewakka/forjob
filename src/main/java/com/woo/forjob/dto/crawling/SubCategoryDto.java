package com.woo.forjob.dto.crawling;

import com.woo.forjob.entity.parameter.SubCategory;
import lombok.Builder;
import lombok.Data;

@Data
public class SubCategoryDto {

    private Long mainId;
    private Long subId;
    private String category;

    @Builder
    public SubCategoryDto(Long mainId, Long subId, String category) {
        this.mainId = mainId;
        this.subId = subId;
        this.category = category;
    }

    public static SubCategoryDto toDto(SubCategory subCategory) {
        return SubCategoryDto.builder()
                .mainId(subCategory.getMainCategory().getId())
                .subId(subCategory.getId())
                .category(subCategory.getSubCategory())
                .build();
    }
}
