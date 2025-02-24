package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.enums.Category;
import co.edu.uniquindio.social_reports.model.vo.Location;

public record ReportInfoDTO(
        String title,
        Category category,
        String description,
        Location location,
        String imageUrl,
        String userId
) {
}
