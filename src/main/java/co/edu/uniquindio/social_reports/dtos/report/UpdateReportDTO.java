package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.entities.Category;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateReportDTO(

        String categoryName,
        String description,
        LocationDTO location,
        @NotBlank List<String> imageUrl,
        @NotBlank String userId
) {
}
