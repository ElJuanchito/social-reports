package co.edu.uniquindio.social_reports.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LogInDTO(
        @Email String email,
        @NotBlank @Min(8) @Max(16) String password
) {
}
