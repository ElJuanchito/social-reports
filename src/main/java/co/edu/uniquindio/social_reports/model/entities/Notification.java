package co.edu.uniquindio.social_reports.model.entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    private ObjectId id;

    private String message;
    private LocalDateTime date;
    private String type;
    private boolean read;
    private ObjectId userId;
    private ObjectId reportId;
}
