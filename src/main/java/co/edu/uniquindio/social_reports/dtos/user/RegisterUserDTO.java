package co.edu.uniquindio.social_reports.dtos.user;

import co.edu.uniquindio.social_reports.model.enums.City;
import jakarta.validation.constraints.*;

public record RegisterUserDTO(
        @NotBlank String name,
        @NotNull(message = "City is required") City city,
        @NotBlank String address,
        @NotBlank @Size(min=10, max=10) String phone,
        @Email @NotBlank String email,
        @NotBlank @Size(min=8, max=16) String password
) {
}
