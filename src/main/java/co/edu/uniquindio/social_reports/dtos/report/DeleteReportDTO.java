package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotBlank;

public record DeleteReportDTO(
        @NotBlank String userID
) {
}
