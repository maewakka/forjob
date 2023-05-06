package com.woo.forjob.entity.recruit;

import com.woo.forjob.entity.auth.User;
import com.woo.forjob.entity.parameter.Region;
import com.woo.forjob.entity.parameter.SubCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class RecruitCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recuit_param_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SubCategory category;

    @Builder
    public RecruitCategory(User user, SubCategory category) {
        this.user = user;
        this.category = category;
    }
}
