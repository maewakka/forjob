package com.woo.forjob.entity.recruit;

import com.woo.forjob.entity.auth.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class RecruitQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_query_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String query;

    @Builder
    public RecruitQuery(User user, String query) {
        this.user = user;
        this.query = query;
    }
}
