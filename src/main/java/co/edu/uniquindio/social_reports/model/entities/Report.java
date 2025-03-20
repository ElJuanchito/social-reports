package co.edu.uniquindio.social_reports.model.entities;

import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import co.edu.uniquindio.social_reports.model.vo.Location;
import co.edu.uniquindio.social_reports.model.vo.ReportHistory;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {

    @Id
    private ObjectId id;

    private List<ReportHistory> history;
    private LocalDateTime date;
    private String description;
    private int importanceCount;
    private ObjectId clientId;
    private String title;
    private Location location;
    private ObjectId categoryId;
    private List<String> images;
    private ReportStatus currentStatus;
}
