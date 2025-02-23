package co.edu.uniquindio.social_reports.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        String name,
        String city,
        String address,
        String phone
) {
}
