package co.edu.uniquindio.social_reports.services.interfaces;

import co.edu.uniquindio.social_reports.dtos.email.EmailDTO;

public interface EmailService {

    void sendEmail(EmailDTO dto) throws Exception;
    void sendHTMLEmail(EmailDTO dto) throws Exception;
}
