package com.woo.forjob.repository.recruit;

import com.woo.forjob.entity.recruit.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {
    boolean existsByTitle(String title);
}
