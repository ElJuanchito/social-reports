package co.edu.uniquindio.social_reports.model.entities;

import co.edu.uniquindio.social_reports.model.enums.City;
import co.edu.uniquindio.social_reports.model.enums.Role;
import co.edu.uniquindio.social_reports.model.enums.UserStatus;
import co.edu.uniquindio.social_reports.model.vo.ValidationCode;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private ObjectId id;

    private String email;
    private Role role;
    private String phone;
    private String password;
    private String name;
    private City city;
    private UserStatus status;
    private ValidationCode validationCode;
    private String address;
}
