package com.woo.forjob.repository.parameter;

import com.woo.forjob.entity.parameter.MainCategory;
import com.woo.forjob.entity.parameter.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByMainCategory(MainCategory mainCategory);
}
