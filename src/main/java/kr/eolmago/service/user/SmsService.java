package kr.eolmago.service.user;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    @Value("${coolsms.apiKey}")
    private String apiKey;

    @Value("${coolsms.secretkey}")
    private String apiSecret;

    @Value("${coolsms.from.number}")
    private String fromNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public void sendVerificationCode(String to, String verificationCode) {
        // 보안: 인증코드를 로그에 기록하지 않음 (프로덕션 로그 노출 방지)
        log.info("SMS 발송 시작: to={}", to);

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(to);
        message.setText("[얼마고] 인증번호는 [" + verificationCode + "] 입니다.");

        try {
            messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("SMS 발송 성공");
        } catch (Exception e) {
            log.error("SMS 발송 실패: {}", e.getMessage());
            // 실서비스에서는 예외 처리가 중요합니다.
            // throw new RuntimeException("SMS 발송에 실패했습니다.");
        }
    }
}
