package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusDTO(
        @NotBlank String userId,
        @NotBlank String reason,
        @NotBlank String reportId
) {
}
