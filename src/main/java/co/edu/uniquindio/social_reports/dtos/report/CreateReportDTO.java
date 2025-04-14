package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record CreateReportDTO(
        @NotBlank String title,
        @NotNull String categoryName,
        @NotBlank String description,
        @NotNull LocationDTO location,
        @NotBlank List<String> imageUrl,
        @NotBlank String userId
) {
}
