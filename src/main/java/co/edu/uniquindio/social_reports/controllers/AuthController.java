package co.edu.uniquindio.social_reports.controllers;

import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.reponses.MessageDTO;
import co.edu.uniquindio.social_reports.dtos.user.LogInDTO;
import co.edu.uniquindio.social_reports.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
