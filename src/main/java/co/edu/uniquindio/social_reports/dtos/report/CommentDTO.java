package co.edu.uniquindio.social_reports.dtos.report;

import jakarta.validation.constraints.NotBlank;

public record CommentDTO(
        @NotBlank String userId,
        @NotBlank String comment
) {
}
