package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.dtos.report.CategoryDTO;
import co.edu.uniquindio.social_reports.dtos.report.RefuseReportDTO;
import co.edu.uniquindio.social_reports.dtos.report.ReportInfoDTO;
import co.edu.uniquindio.social_reports.dtos.report.ViewReportDTO;
import co.edu.uniquindio.social_reports.dtos.user.UserInfoDTO;
import co.edu.uniquindio.social_reports.model.entities.Category;
import co.edu.uniquindio.social_reports.services.interfaces.ReportService;
import co.edu.uniquindio.social_reports.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "admin")
public class AdminController {

    private final ReportService reportService;
    private final UserService userService;

    @Operation(summary = "Obtener reportes por categoria", description = "Obtinene todos los reportes de una cateoria")
    @GetMapping("/report")
    public ResponseEntity<MessageDTO<List<ReportInfoDTO>>> getReportsByCategory(@RequestParam Category category) throws Exception {
        List<ReportInfoDTO> reportsInfoByCategory = reportService.getReportsInfoByCategory(category);
        return ResponseEntity.ok(new MessageDTO<>(false, reportsInfoByCategory));
    }

    @Operation(summary = "Verificar reporte", description = "Marca un reporte como verificado por un admin")
    @PutMapping("/check/{id}")
    public ResponseEntity<MessageDTO<String>> checkReport(@PathVariable String id) throws Exception {
        reportService.checkReport(id);
        return ResponseEntity.ok(new MessageDTO<>(false, "Report check successful"));
    }

    @Operation(summary = "Rechazar reporte", description = "Rechaza un reporte por diferentes motivos determinados por el admin")
    @PostMapping("/refuse")
    public ResponseEntity<MessageDTO<String>> refuseReport(@Valid @RequestBody RefuseReportDTO reportDTO) throws Exception {
        reportService.refuseReport(reportDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Report refuse successful"));
    }

    @Operation(summary = "Marcar como resuelto", description = "El admin marca un reporte como resuelto")
    @PostMapping("/{id}/resolved")
    public ResponseEntity<MessageDTO<String>> setAsResolved(@PathVariable String id) throws Exception {
        reportService.setAsResolved(id);
        return ResponseEntity.ok(new MessageDTO<>(false, "Report resolved successfuly"));
    }

    @Operation(summary = "Obtener usuarios", description = "Obtiene la informacion todos los usuarios guardados en la base de datos")
    @GetMapping("/users")
    public ResponseEntity<MessageDTO<List<UserInfoDTO>>> getAllUsers() throws Exception {
        List<UserInfoDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new MessageDTO<>(false, users));
    }

    @Operation(summary = "Generar reporte", description = "Genera y envia un reporte en pdf al correo del admin")
    @PostMapping("/view")
    public ResponseEntity<MessageDTO<String>> generateViewReport(@Valid @RequestBody ViewReportDTO dto) throws Exception {
        reportService.createViewReport(dto);
        return ResponseEntity.ok(new MessageDTO<>(false, "View report genrated successfully"));
    }

    @Operation(summary = "Crear categoria", description = "permitir crear una nueva categoria")
    @PostMapping("/category")
    public ResponseEntity<MessageDTO<String>> createCategory(@RequestBody CategoryDTO dto) throws Exception {
        reportService.createCategory(dto);
        return ResponseEntity.ok(new MessageDTO<>(false, "Category created successfully"));
    }
}
