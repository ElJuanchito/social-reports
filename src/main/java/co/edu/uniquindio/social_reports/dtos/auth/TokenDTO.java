package co.edu.uniquindio.social_reports.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TokenDTO(
        String token
) {
}
