package com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MetaDataDto {
    private int page;
    private int totalPage;
    private int perPage;
    private long total;

    @Builder
    public MetaDataDto(int page, int totalPage, int perPage, long total) {
        this.page = page;
        this.totalPage = totalPage;
        this.perPage = perPage;
        this.total = total;
    }
}
