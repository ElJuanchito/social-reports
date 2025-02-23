package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.user.RegisterUserDTO;
import co.edu.uniquindio.social_reports.dtos.user.UpdateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "users")
public class UserController {

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en la base de datos")
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        // userService.registerUser(registerUserDTO);
        return ResponseEntity.status(200).body("User registered successfully");
    }

    @Operation(summary = "Actualizar datos del usuario", description = "Actualiza los datos de un usuario especifico mediante su id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO) {
        // userService.updateUser(id, updateUser);
        return ResponseEntity.status(200).body("User updated successfully");
    }
}
