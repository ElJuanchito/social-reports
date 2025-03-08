package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViewReportDTO(
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
) {
}
