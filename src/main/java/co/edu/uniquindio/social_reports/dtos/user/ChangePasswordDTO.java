package co.edu.uniquindio.social_reports.dtos.user;

import jakarta.validation.constraints.*;

public record ChangePasswordDTO(
        @Email String email,
        @NotBlank String recoverCode,
        @NotBlank @Size(min=8, max=16) String newPassword
) {
}
