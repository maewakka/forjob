package com.woo.forjob.entity.parameter;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CrawlingWebSite webSite;
    private String year;
    private String item;
    private String value;

    @Builder
    public Career(CrawlingWebSite webSite, String year, String item, String value) {
        this.webSite = webSite;
        this.year = year;
        this.item = item;
        this.value = value;
    }
}
