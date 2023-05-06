package com.woo.forjob.dto.crawling;

import lombok.Builder;
import lombok.Data;

@Data
public class Param {

    private String item;
    private String value;

    @Builder
    public Param(String item, String value) {
        this.item = item;
        this.value = value;
    }
}
