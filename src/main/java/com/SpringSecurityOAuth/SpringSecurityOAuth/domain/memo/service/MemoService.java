package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao.MemoRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.domain.Memo;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoWriteDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoIdDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemosDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.exception.MemoAccessDeniedException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.exception.MemoNotFoundException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.MetaDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final UserService userService;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoIdDto writeMemo(MemoWriteDto memoWriteDto) {
        String email = getPrincipalEmail();
        User user = userService.getUserByEmail(email);

        Memo memo = Memo.builder()
                .memo(memoWriteDto.getMemo())
                .user(user)
                .build();

        memoRepository.save(memo);

        return new MemoIdDto(memo.getId());
    }

    @Transactional(readOnly = true)
    public MemosDto getMemos(int page, int perPage) {
        String email = getPrincipalEmail();
        User user = userService.getUserByEmail(email);

        Page<Memo> memoPagingResult = memoRepository.findByUser(user, PageRequest.of(page, perPage));

        // 메타데이터
        MetaDataDto metaData = MetaDataDto.builder()
                .page(page)
                .totalPage(memoPagingResult.getTotalPages())
                .perPage(perPage)
                .total(memoPagingResult.getTotalElements())
                .build();

        // 실제 데이터
        List<MemoDto> memos = memoPagingResult.getContent()
                .stream().map(memo -> MemoDto.builder()
                        .id(memo.getId())
                        .memo(memo.getMemo())
                        .createdAt(memo.getCreatedAt())
                        .updatedAt(memo.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return MemosDto.builder()
                .metaData(metaData)
                .memos(memos)
                .build();
    }

    @Transactional(readOnly = true)
    public MemoDto getMemo(Long memoId) {
        String email = getPrincipalEmail();

        Memo memo = memoRepository.findByIdWithUser(memoId).orElseThrow(()->new MemoNotFoundException());
        if(!memo.getUser().getEmail().equals(email)) throw new MemoAccessDeniedException();

        return MemoDto.builder()
                .id(memo.getId())
                .memo(memo.getMemo())
                .createdAt(memo.getCreatedAt())
                .updatedAt(memo.getUpdatedAt())
                .build();
    }

    @Transactional
    public MemoIdDto updateMemo(Long memoId, MemoWriteDto memoUpdateDto) {
        String email = getPrincipalEmail();

        Memo memo = memoRepository.findByIdWithUser(memoId).orElseThrow(()->new MemoNotFoundException());
        if(!memo.getUser().getEmail().equals(email)) throw new MemoAccessDeniedException();

        Memo updatedMemo = memo.updateMemo(memoUpdateDto.getMemo());

        return new MemoIdDto(updatedMemo.getId());
    }


    private String getPrincipalEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto)authentication.getPrincipal();
        return principal.getEmail();
    }
}
