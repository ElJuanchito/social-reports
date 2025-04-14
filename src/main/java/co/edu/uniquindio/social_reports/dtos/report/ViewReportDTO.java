package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ViewReportDTO(
        @NotNull @NotBlank String adminId,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotBlank String categoryName
) {
}
