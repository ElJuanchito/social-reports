package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.dtos.report.*;
import co.edu.uniquindio.social_reports.model.enums.City;
import co.edu.uniquindio.social_reports.model.enums.ReportStatus;
import co.edu.uniquindio.social_reports.services.interfaces.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Crear reporte", description = "Crea un reporte con los datos necesarios y lo publica en la plataforma")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDTO<String>> createReport(
            @RequestPart(value = "data") @Parameter(schema = @Schema(type = "string", format = "binary")) final CreateReportDTO reportDTO,
            @RequestPart(value = "image", required = false) final MultipartFile[] images
    ) throws Exception {
        reportService.createReport(reportDTO, images);
        return ResponseEntity.status(201).body(new MessageDTO<>(false, "Report created successfully"));
    }

    @Operation(summary = "Editar reporte", description = "Edita un reporte con los datos proporcionados en el cuerto de la peticion")
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDTO<String>> updateReport(
            @PathVariable String id,
            @RequestPart(value = "data") @Parameter(schema = @Schema(type = "string", format = "binary")) final UpdateReportDTO updateReportDTO,
            @RequestPart(value = "image", required = false) final MultipartFile[] images
    ) throws Exception {
        reportService.updateReport(id, updateReportDTO, images);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Report updated successfully"));
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte de la base de datos mediante el id del reporte")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO<String>> deleteReport(@PathVariable String id, @RequestBody DeleteReportDTO dto) throws Exception {
        reportService.deleteReport(id, dto);
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

    @Operation(summary = "Cambiar estado del reporte", description = "Cambia el estado de los reportes a resuelto mediante su id")
    @PostMapping("/changeStatus")
    public ResponseEntity<MessageDTO<String>> changeStatus( @RequestBody ChangeStatusDTO statusDTO) throws Exception {
        reportService.changeStatus(statusDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Report status changed successfully"));
    }

    @Operation(summary = "Obtener lista de ciudades", description = "Se obtiene la lista con todas las ciudades disponibles para los reportes")
    @GetMapping("/cities")
    public ResponseEntity< MessageDTO<List<City>> > getCities() throws Exception {
        List<City> cities = reportService.getCities();
        return ResponseEntity.status(200).body(new MessageDTO<>(false, cities));
    }

    @Operation(summary = "Obtener lista de estatus", description = "Se obtiene la lista con todas los estatus disponibles para los reportes")
    @GetMapping("/statuses")
    public ResponseEntity< MessageDTO<List<ReportStatus>> > getStatuses() throws Exception {
        List<ReportStatus> statuses = reportService.getStatuses();
        return ResponseEntity.status(200).body(new MessageDTO<>(false, statuses));
    }

    @Operation(summary = "Obtener lista de categorias", description = "Se obtiene la lista con todas las categorias disponibles para los reportes")
    @GetMapping("/categories")
    public ResponseEntity< MessageDTO<List<CategoryDTO>> > getCategories() throws Exception {
        List<CategoryDTO> categories = reportService.getCategories();
        return ResponseEntity.status(200).body(new MessageDTO<>(false, categories));
    }




}
