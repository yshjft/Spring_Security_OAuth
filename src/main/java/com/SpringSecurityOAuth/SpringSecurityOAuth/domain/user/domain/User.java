package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.domain.Post;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

    // post
    @Builder
    public User(Long id, String name, String email, String picture, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User updateUser(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRole() {
        return this.role.getRole();
    }
}
