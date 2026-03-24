package kr.eolmago.dto.api.user.response;

// CODE REVIEW: jk-Nam 작업

import java.util.UUID;

public record UserInfoResponse(
        UUID id,
        String email,
        String nickname,
        String role
) {
}
