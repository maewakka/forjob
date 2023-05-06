package com.woo.forjob.entity.parameter;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@NoArgsConstructor
@Slf4j
@Data
@Entity
public class MainCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_category_id")
    private Long id;

    private String category;

    @Builder
    public MainCategory(String category) {
        this.category = category;
    }
}
