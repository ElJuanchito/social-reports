package co.edu.uniquindio.social_reports.dtos.report;

import co.edu.uniquindio.social_reports.model.entities.Category;
import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import co.edu.uniquindio.social_reports.model.vo.Location;

import java.util.List;

public record ReportInfoDTO(
        String title,
        Category category,
        String description,
        Location location,
        List<String> imageUrl,
        String userId,
        ReportStatus reportStatus
) {
}
