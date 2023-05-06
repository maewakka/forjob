package com.woo.forjob.entity.parameter;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CrawlingWebSite webSite;
    private String name;
    private String item;
    private String value;

    @Builder
    public Region(CrawlingWebSite webSite, String name, String item, String value) {
        this.webSite = webSite;
        this.name = name;
        this.item = item;
        this.value = value;
    }
}
