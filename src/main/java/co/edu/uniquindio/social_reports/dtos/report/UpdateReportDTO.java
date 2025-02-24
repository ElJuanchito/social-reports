package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.enums.Category;
import co.edu.uniquindio.social_reports.model.vo.Location;
import jakarta.validation.constraints.NotBlank;

public record UpdateReportDTO(
        Category category,
        String description,
        Location location,
        @NotBlank String imageUrl,
        @NotBlank String userId
) {
}
