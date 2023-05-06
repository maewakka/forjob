package com.woo.forjob.entity.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @Column(name = "user_id", unique = true)
    private Long id;

    private String email;
    private String nickname;
    private String profileUrl;

    @Builder
    public User(Long id, String email, String nickname, String profileUrl) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
