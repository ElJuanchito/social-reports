package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReportDTO(
        @NotBlank String title,
        @NotBlank String categoryName,
        @NotBlank String description,
        @NotNull LocationDTO location,
        @NotBlank String userId
) {
}
