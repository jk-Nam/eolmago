package kr.eolmago.controller.api.user;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.eolmago.dto.api.user.request.LoginRequest;
import kr.eolmago.dto.api.user.response.TokenResponse;
import kr.eolmago.global.security.CustomUserDetails;
import kr.eolmago.global.security.filter.JwtAuthenticationFilter;
import kr.eolmago.service.user.AuthService;
import kr.eolmago.service.user.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "권한")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    // 환경별 쿠키 보안 설정 (개발: false, 프로덕션: true)
    @Value("${cookie.secure:false}")
    private boolean secureCookie;

    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        TokenResponse tokenResponse = authService.login(request);

        addCookie(response,
                JwtAuthenticationFilter.ACCESS_TOKEN_COOKIE,
                tokenResponse.accessToken(),
                jwtService.getAccessTokenExpirySeconds());
        addCookie(response,
                REFRESH_TOKEN_COOKIE,
                tokenResponse.refreshToken(),
                jwtService.getRefreshTokenExpirySeconds());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @CookieValue(name = REFRESH_TOKEN_COOKIE, required = false) String cookieToken,
            @RequestBody(required = false) Map<String, String> body,
            HttpServletResponse response
    ) {
        String refreshToken = cookieToken;
        if (refreshToken == null && body != null) {
            refreshToken = body.get("refreshToken");
        }
        if (refreshToken == null) {
            throw new BadCredentialsException("Refresh Token이 필요합니다.");
        }

        TokenResponse tokenResponse = authService.refresh(refreshToken);

        addCookie(response,
                JwtAuthenticationFilter.ACCESS_TOKEN_COOKIE,
                tokenResponse.accessToken(),
                jwtService.getAccessTokenExpirySeconds());
        addCookie(response,
                REFRESH_TOKEN_COOKIE,
                tokenResponse.refreshToken(),
                jwtService.getRefreshTokenExpirySeconds());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            HttpServletResponse response,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        authService.logout(UUID.fromString(userDetails.getId()));

        removeCookie(response, JwtAuthenticationFilter.ACCESS_TOKEN_COOKIE);
        removeCookie(response, REFRESH_TOKEN_COOKIE);

        return ResponseEntity.ok(Map.of("message", "로그아웃 완료"));
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> inactive(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // 본인의 세션만 무효화 가능 (보안 취약점 수정)
        authService.logout(user.getUserId());
        return ResponseEntity.ok(Map.of("message", "세션이 무효화되었습니다."));
    }

    private void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            long maxAge
    ) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(secureCookie)  // 환경별 설정 (개발: false, 프로덕션: true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void removeCookie(
            HttpServletResponse response,
            String name
    ) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(secureCookie)  // 환경별 설정 (개발: false, 프로덕션: true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
