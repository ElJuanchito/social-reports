package co.edu.uniquindio.social_reports.services;

import co.edu.uniquindio.social_reports.dtos.EmailDTO;

public interface EmailService {

    void sendEmail(EmailDTO dto) throws Exception;
}
