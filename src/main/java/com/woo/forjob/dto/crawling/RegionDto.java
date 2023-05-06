package com.woo.forjob.dto.crawling;

import com.woo.forjob.entity.parameter.Region;
import lombok.Builder;
import lombok.Data;

@Data
public class RegionDto {

    private Long id;
    private String name;

    @Builder
    public RegionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RegionDto toDto(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
