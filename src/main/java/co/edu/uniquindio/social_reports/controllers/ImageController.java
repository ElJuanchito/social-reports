package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.services.interfaces.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Tag(name = "images")
@SecurityRequirement(name = "bearerAuth")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Subir imagen", description = "Permite subir una imagen a la base de datos")
    @PostMapping("/upload")
    public ResponseEntity<MessageDTO<String>> uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        String url = imageService.uploadImage(file);
        return ResponseEntity.ok(new MessageDTO<>(false, url));
    }

    @Operation(summary = "Subir imagen", description = "Permite subir una imagen a la base de datos")
    @PostMapping("/delete")
    public ResponseEntity<MessageDTO<Map>> deleteImage(@RequestParam String id) throws Exception {
        Map map = imageService.deleteImage(id);
        return ResponseEntity.ok(new MessageDTO<>(false, map));
    }
}
