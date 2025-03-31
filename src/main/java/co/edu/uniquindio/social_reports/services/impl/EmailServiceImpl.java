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

    @Override
    @Async
    public void sendEmail(EmailDTO dto) throws Exception {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.to());
        message.setSubject(dto.subject());

        mailSender.send(message);
    }

//    @Async
//    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlBody, true);
//
//        mailSender.send(message);
//    }
}
