package co.edu.uniquindio.social_reports.dtos;

public record MessageDTO<T>(
        boolean error,
        T message
) {
}
