package co.edu.uniquindio.social_reports.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(
    @NotBlank String name,
    @NotBlank String city,
    @NotBlank String address,
    @NotBlank @Max(10) @Min(10) String phone,
    @Email @NotBlank String email,
    @NotBlank @Min(8) @Max(16) String password
) {
}
