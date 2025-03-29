package co.edu.uniquindio.social_reports.dtos.user;

import co.edu.uniquindio.social_reports.model.enums.City;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        String name,
        City city,
        String address,
        String phone
) {
}
