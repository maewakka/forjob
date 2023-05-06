package com.woo.forjob.entity.recruit;

import com.woo.forjob.entity.auth.User;
import com.woo.forjob.entity.parameter.Region;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class RecruitRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_region_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Builder
    public RecruitRegion(User user, Region region) {
        this.user = user;
        this.region = region;
    }
}
