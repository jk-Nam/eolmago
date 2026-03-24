package kr.eolmago.dto.api.user.response;

// CODE REVIEW: jk-Nam 작업

import java.time.OffsetDateTime;

public record UserPenaltyInfoResponse(
        String type,
        String reason,
        OffsetDateTime expiresAt
) {
    public static UserPenaltyInfoResponse of(String type, String reason, OffsetDateTime expiresAt) {
        return new UserPenaltyInfoResponse(type, reason, expiresAt);
    }
}
