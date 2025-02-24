package co.edu.uniquindio.social_reports.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(
        @Email String email,
        @NotBlank String recoverCode,
        @NotBlank @Max(16) @Min(8) String newPassword
) {
}
