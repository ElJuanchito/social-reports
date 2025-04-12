package co.edu.uniquindio.social_reports.dtos.user;

import jakarta.validation.constraints.*;

public record LogInDTO(
        @Email String email,
        @NotBlank @Size(min=8, max=16) String password
) {
}
