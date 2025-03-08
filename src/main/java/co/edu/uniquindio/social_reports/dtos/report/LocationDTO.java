package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotBlank;

public record LocationDTO(
        @NotBlank String latitude,
        @NotBlank String longitude
) {
}
