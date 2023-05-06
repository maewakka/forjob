package com.woo.forjob.entity.recruit;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class RecruitPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_post_id")
    private Long id;

    // 회사이름
    private String company;
    // 공고제목
    private String title;
    // 경력
    private String career;
    // 학력
    private String education;
    // 위치
    private String location;
    // 근무타입
    private String workType;
    // 마감일
    private String deadline;
    // 등록/수정일
    private String lastModified;

    @Builder
    public RecruitPost(String company, String title, String career, String education, String location, String workType, String deadline, String lastModified) {
        this.company = company;
        this.title = title;
        this.career = career;
        this.education = education;
        this.location = location;
        this.workType = workType;
        this.deadline = deadline;
        this.lastModified = lastModified;
    }
}
