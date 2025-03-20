package co.edu.uniquindio.social_reports.model.vo;

import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportHistory {

    private String comments;
    private ObjectId clientId;
    private ReportStatus status;
    private LocalDateTime date;
}
