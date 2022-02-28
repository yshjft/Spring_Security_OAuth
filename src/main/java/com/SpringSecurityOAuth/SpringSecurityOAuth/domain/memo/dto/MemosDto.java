package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto;

import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.MetaDataDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemosDto {
    private MetaDataDto metaData;
    private List<MemoDto> memos;

    @Builder
    public MemosDto(MetaDataDto metaData, List<MemoDto> memos) {
        this.metaData = metaData;
        this.memos = memos;
    }
}
