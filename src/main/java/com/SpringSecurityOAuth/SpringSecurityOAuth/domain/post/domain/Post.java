package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.domain;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Size(max = 300)
    @Column(nullable = false)
    private String memo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Builder
    public Post(Long id, String memo, User user) {
        this.id = id;
        this.memo = memo;
        this.user = user;
        user.getPosts().add(this);
    }
}
