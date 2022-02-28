package com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
public class PageParamsDto {
    @Min(0)
    @NotNull
    private int page;

    @Min(1)
    @NotNull
    private int perPage;
}
