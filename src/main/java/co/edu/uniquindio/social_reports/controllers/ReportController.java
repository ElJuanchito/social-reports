package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Crear reporte", description = "Crea un reporte con los datos necesarios y lo publica en la plataforma")
    @PostMapping
    public ResponseEntity<MessageDTO<String>> createReport(@Valid @RequestBody CreateReportDTO reportDTO) throws Exception {
        reportService.createReport(reportDTO);
        return ResponseEntity.status(201).body(new MessageDTO<>(false, "Report created successfully"));
    }

    @Operation(summary = "Editar reporte", description = "Edita un reporte con los datos proporcionados en el cuerto de la peticion")
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO<String>> updateReport(@PathVariable String id, @Valid @RequestBody UpdateReportDTO reportDTO) throws Exception {
        reportService.updateReport(id, reportDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Report updated successfully"));
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte de la base de datos mediante el id del reporte")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO<String>> deleteReport(@PathVariable String id) throws Exception {
        reportService.deleteReport(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Report deleted successfully"));
    }

    @Operation(summary = "Obtener informacion del reporte", description = "Entrega la informacion del reporte mediante su id")
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO<ReportInfoDTO>> getReportInfo(@PathVariable String id) throws Exception {
        ReportInfoDTO reportInfo = reportService.getReportInfo(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, reportInfo));
    }

    @Operation(summary = "Agregar comentario", description = "Permite agregar un comentario a un reporte, mediante el id del reporte y los datos del comentario")
    @PostMapping("/{id}/comments")
    public ResponseEntity<MessageDTO<String>> addCommentToReport(@PathVariable String id, @Valid @RequestBody CommentDTO commentDTO) throws Exception {
        reportService.addCommentToReport(id, commentDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Comment added to report successfully"));
    }

    @Operation(summary = "Marcar reporte como importante", description = "Marca un reporte seleccionado por su id como importante por un usuario")
    @PostMapping("/{id}/important")
    public ResponseEntity<MessageDTO<String>> setAsImportant(@PathVariable String id, @RequestParam String userId) throws Exception {
        reportService.setAsImportant(id,userId);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "The report has been marked as important"));
    }

    @Operation(summary = "Cambiar estado del reporte", description = "Cambia el estado de los reportes a resuelto u otro, mediante su id")
    @PostMapping("/{id}/status")
    public ResponseEntity<MessageDTO<String>> changeStatus(@PathVariable String id, @RequestBody ChangeStatusDTO statusDTO) throws Exception {
        reportService.changeStatus(id, statusDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Report status changed successfully"));
    }








}
