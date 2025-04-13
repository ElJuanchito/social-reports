package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.entities.Category;
import jakarta.validation.constraints.NotBlank;

public record UpdateReportDTO(
        String title,
        String categoryName,
        String description,
        LocationDTO location,
        @NotBlank String userId
) {
}
