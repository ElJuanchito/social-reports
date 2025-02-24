package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.enums.Category;
import co.edu.uniquindio.social_reports.model.vo.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReportDTO(
        @NotBlank String title,
        @NotNull Category category,
        @NotBlank String description,
        @NotNull Location location,
        @NotBlank String imageUrl,
        @NotBlank String userId
) {
}
