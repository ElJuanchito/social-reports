package co.edu.uniquindio.social_reports.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationCode {

    private LocalDateTime date;
    private String code;
}
