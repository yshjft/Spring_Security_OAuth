package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dao.PostRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.domain.Post;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto.PostRequestDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto.PostResponseDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto writeMemo(PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto)authentication.getPrincipal();

        String email = principal.getEmail();
        User user = userService.getUserByEmail(email);

        Post post = Post.builder()
                .memo(postRequestDto.getMemo())
                .user(user)
                .build();

        postRepository.save(post);

        return new PostResponseDto(post.getId());
    }
}
