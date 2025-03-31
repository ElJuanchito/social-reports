package co.edu.uniquindio.social_reports.dtos.email;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank String subject,
        @NotBlank String body,
        @NotBlank String to
) {
}
