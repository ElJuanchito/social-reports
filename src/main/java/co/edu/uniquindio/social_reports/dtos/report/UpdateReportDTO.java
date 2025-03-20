package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.entities.Category;
import jakarta.validation.constraints.NotBlank;

public record UpdateReportDTO(
        Category category,
        String description,
        LocationDTO location,
        @NotBlank String imageUrl,
        @NotBlank String userId
) {
}
