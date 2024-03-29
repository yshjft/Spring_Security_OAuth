package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao.MemoRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.domain.Memo;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoIdDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.exception.MemoAccessDeniedException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.exception.MemoNotFoundException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemoServiceTest {
    @InjectMocks
    MemoService memoService;
    @Mock
    UserService userService;
    @Mock
    MemoRepository memoRepository;
    @Mock
    SecurityContext securityContext;

    @BeforeEach
    void init() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, "");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void 메모_작성() {
        // given
        when(userService.getUserByEmail("test@test.com")).thenReturn(user);
        when(memoRepository.save(any())).thenReturn(memo);

        // when
        MemoIdDto memoResponseDto = memoService.writeMemo(memoWriteDto);

        // then
        verify(userService).getUserByEmail("test@test.com");
        verify(memoRepository).save(any());

        assertThat(memoResponseDto).isNotNull();
    }

    @Test
    public void 메모_조회() {
        // given
        List<Memo> memos = new ArrayList<>();
        memos.add(memo);
        Page<Memo> memoPagingResult = new PageImpl<>(memos);

        int page = 0, perPage = 3;
        Pageable pageable = PageRequest.of(page, perPage);

        when(userService.getUserByEmail(userDto.getEmail())).thenReturn(user);
        when(memoRepository.findByUser(user, pageable)).thenReturn(memoPagingResult);

        // when
        memoService.getMemos(page, perPage);

        // then
        verify(userService).getUserByEmail(userDto.getEmail());
        verify(memoRepository).findByUser(user, pageable);
    }


    @Test
    public void 메모_단건_조회() {
        // given
        when(memoRepository.findByIdWithUser(memo.getId())).thenReturn(Optional.of(memo));

        // when
        MemoDto memoDto = memoService.getMemo(memo.getId());

        // then
        verify(memoRepository).findByIdWithUser(memo.getId());

        assertThat(memoDto.getId()).isEqualTo(memo.getId());
    }

    @Test
    public void 메모_단건_조회_실패() {
        // given
        when(memoRepository.findByIdWithUser(memo.getId())).thenThrow(new MemoNotFoundException());

        try {
            // when
            memoService.getMemo(memo.getId());
        }catch(Exception e) {
            assertThat(e instanceof MemoNotFoundException).isTrue();
        }finally {
            verify(memoRepository).findByIdWithUser(memo.getId());
        }
    }

    @Test
    public void 메모_단건_조회_실패_접근_거부() {
        // given
        when(memoRepository.findByIdWithUser(memo2.getId())).thenReturn(Optional.of(memo2));

        try {
            // when
            memoService.getMemo(memo2.getId());
        }catch(Exception e) {
            assertThat(e instanceof MemoAccessDeniedException).isTrue();
        }finally {
            verify(memoRepository).findByIdWithUser(memo2.getId());
        }
    }

    @Test
    public void 메모_수정() {
        // given
        when(memoRepository.findByIdWithUser(memo.getId())).thenReturn(Optional.of(memo));

        // when
        memoService.updateMemo(memo.getId(), memoUpdateDto);

        // then
        verify(memoRepository).findByIdWithUser(memo.getId());
    }

    @Test
    public void 메모_수정_실패() {
        // given
        when(memoRepository.findByIdWithUser(memo2.getId())).thenReturn(Optional.of(memo2));

        try{
            memoService.updateMemo(memo2.getId(), memoUpdateDto);
        }catch (Exception e) {
            assertThat(e instanceof MemoAccessDeniedException).isTrue();
        }
    }

    @Test
    public void 메모_삭제() {
        // given
        when(memoRepository.findByIdWithUser(memo.getId())).thenReturn(Optional.of(memo));

        // when
        memoService.deleteMemo(memo.getId());

        // then
        verify(memoRepository).delete(memo);
    }

    @Test
    public void 메모_삭제_실패_접근_거부() {
        // given
        when(memoRepository.findByIdWithUser(memo2.getId())).thenReturn(Optional.of(memo2));

        try {
            memoService.deleteMemo(memo2.getId());
        }catch (Exception e) {
            verify(memoRepository, never()).delete(memo);
            assertThat(e instanceof MemoAccessDeniedException);
        }
    }
}