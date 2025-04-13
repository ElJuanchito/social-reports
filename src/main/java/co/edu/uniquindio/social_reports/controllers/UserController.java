package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.dtos.user.ChangePasswordDTO;
import co.edu.uniquindio.social_reports.dtos.user.UpdateUserDTO;
import co.edu.uniquindio.social_reports.dtos.user.UserInfoDTO;
import co.edu.uniquindio.social_reports.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "users")
public class UserController {

    private final UserService userService;

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar datos del usuario", description = "Actualiza los datos de un usuario especifico mediante su id")
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO<String>> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserDTO updateUserDTO) throws Exception {
        userService.updateUser(id, updateUserDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "User updated successfully"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar usuario", description = "Marca como eliminado a un usuario de la base de datos mediante su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO<String>> deleteUser(@PathVariable String id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "User deleted successfully"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "obtener informacion de usuario", description = "Entrega la informacion del usuario mediante su id")
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO<UserInfoDTO>> getUserInfo(@PathVariable String id) throws Exception {
        UserInfoDTO userIfo = userService.getUserInfo(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, userIfo));
    }
}
