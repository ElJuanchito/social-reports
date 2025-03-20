package co.edu.uniquindio.social_reports.model.entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    private ObjectId id;

    private ObjectId reportId;
    private String message;
    private LocalDateTime date;
    private ObjectId clientId;
}
