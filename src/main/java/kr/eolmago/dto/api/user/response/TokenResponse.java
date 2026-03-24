package kr.eolmago.dto.api.user.response;

// CODE REVIEW: jk-Nam 작업

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn
) {
}
