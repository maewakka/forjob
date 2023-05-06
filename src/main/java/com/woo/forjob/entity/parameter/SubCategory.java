package com.woo.forjob.entity.parameter;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "main_category")
    private MainCategory mainCategory;
    private String subCategory;
    private String item;
    private String value;

    @Builder
    public SubCategory(MainCategory mainCategory, String subCategory, String item, String value) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.item = item;
        this.value = value;
    }
}
