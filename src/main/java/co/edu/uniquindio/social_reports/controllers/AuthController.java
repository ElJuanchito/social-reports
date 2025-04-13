package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.dtos.user.ChangePasswordDTO;
import co.edu.uniquindio.social_reports.dtos.user.LogInDTO;
import co.edu.uniquindio.social_reports.dtos.user.RegisterUserDTO;
import co.edu.uniquindio.social_reports.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Inciar sesion", description = "Permite iniciar sesion en la plataforma mediante el correo y la contrena")
    @PostMapping("/login")
    public ResponseEntity<MessageDTO<TokenDTO>> logIn(@RequestBody @Valid LogInDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = userService.logIn(loginDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, tokenDTO));
    }

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en la base de datos")
    @PostMapping("/register")
    public ResponseEntity<MessageDTO<String>> registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO) throws Exception {
        userService.registerUser(registerUserDTO);
        return ResponseEntity.status(201).body(new MessageDTO<>(false, "User registered successfully"));
    }

    @Operation(summary = "Activar usuario", description = "Activa un usuario mediante la validacion del codigo que recibio al correo")
    @PostMapping("/{email}/validateUser")
    public ResponseEntity<MessageDTO<String>> validateRegisterCode(@PathVariable String email, @RequestParam String code) throws Exception {
        userService.activateUser(email, code);
        return ResponseEntity.ok(new MessageDTO<>(false, "User activated successfully"));
    }

    @Operation(summary = "Reenviar codigo de activacion", description = "Reenvia el codigo de activacion al correo del usuario")
    @PostMapping("/{email}/resendActivationCode")
    public ResponseEntity<MessageDTO<String>> resendActivationCode(@PathVariable String email) throws Exception {
        userService.resendActivationCode(email);
        return ResponseEntity.ok(new MessageDTO<>(false, "Activation code resent successfully"));
    }

    @Operation(summary = "Enviar email de recuperacion de contrasena", description = "Envia al usuario un email que contiene el codigo para la recuperacion de la contrasena")
    @PostMapping("/password/recover")
    public ResponseEntity<MessageDTO<String>> sendPasswordResetCode(@Email @RequestParam String email) throws Exception {
        userService.sendPasswordResetCode(email);
        return ResponseEntity.status(202).body(new MessageDTO<>(false, "Password reset code has sent successfully"));
    }

    @Operation(summary = "Cambiar la contrasena del usuario", description = "modifica la contrasena de usuario")
    @PutMapping("/password")
    public ResponseEntity<MessageDTO<String>> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        userService.changePassword(changePasswordDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Password changed successfully"));
    }
}
