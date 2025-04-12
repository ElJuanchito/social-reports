package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.email.EmailDTO;
import co.edu.uniquindio.social_reports.services.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(EmailDTO dto) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.to());
        message.setSubject(dto.subject());
        message.setText(dto.text());

        mailSender.send(message);
    }
}
