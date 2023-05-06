package com.woo.forjob.dto.crawling;

import com.woo.forjob.entity.parameter.MainCategory;
import lombok.Builder;
import lombok.Data;

@Data
public class MainCategoryDto {
    private Long id;
    private String category;

    @Builder
    public MainCategoryDto(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public static MainCategoryDto toDto(MainCategory mainCategory) {
        return MainCategoryDto.builder()
                .id(mainCategory.getId())
                .category(mainCategory.getCategory())
                .build();
    }
}
