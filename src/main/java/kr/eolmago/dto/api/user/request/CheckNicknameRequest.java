package kr.eolmago.dto.api.user.request;

// CODE REVIEW: jk-Nam 작업

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckNicknameRequest(
        @NotBlank(message = "닉네임은 필수입니다")
        @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하여야 합니다")
        String nickname
) {

}
