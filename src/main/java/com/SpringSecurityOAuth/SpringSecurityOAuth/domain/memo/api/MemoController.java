package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoWriteDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoIdDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemosDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.service.MemoService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.request.PageParamsDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    // 생성
    @PostMapping
    public ResponseEntity<ResponseDto> writeMemo(@Validated @RequestBody MemoWriteDto memoWriteDto) {
        MemoIdDto memoResponseDto = memoService.writeMemo(memoWriteDto);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("write memo successfully")
                .result(memoResponseDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto> getMemos(@Validated @ModelAttribute PageParamsDto pageParamsDto) {
        MemosDto memosDto = memoService.getMemos(pageParamsDto.getPage(), pageParamsDto.getPerPage());

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("get memos successfully")
                .result(memosDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // 단건 조회
    @GetMapping("/{memoId}")
    public ResponseEntity<ResponseDto> getMemo(@PathVariable Long memoId) {
        MemoDto memoDto= memoService.getMemo(memoId);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("get memo(id:"+memoId+") successfully.")
                .result(memoDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // 수정
    @PutMapping("/{memoId}")
    public ResponseEntity<ResponseDto> updateMemo(@PathVariable Long memoId, @Validated @RequestBody MemoWriteDto memoUpdateDto) {
        MemoIdDto memoIdDto = memoService.updateMemo(memoId, memoUpdateDto);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("update memo(id:"+memoId+") successfully.")
                .result(memoIdDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // 삭제
    @DeleteMapping("/{memoId}")
    public ResponseEntity<ResponseDto> deleteMemo(@PathVariable Long memoId) {
        MemoIdDto memoIdDto = memoService.deleteMemo(memoId);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("delete memo(id:"+memoId+") successfully.")
                .result(memoIdDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    // bulk 삭제
}
