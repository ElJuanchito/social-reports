package co.edu.uniquindio.social_reports.dtos.reponses;

public record MessageDTO<T>(
        boolean error,
        T message
) {
}
