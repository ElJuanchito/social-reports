package co.edu.uniquindio.social_reports.tests;

import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.user.*;
import co.edu.uniquindio.social_reports.model.entities.User;
import co.edu.uniquindio.social_reports.model.enums.City;
import co.edu.uniquindio.social_reports.model.enums.UserStatus;
import co.edu.uniquindio.social_reports.model.vo.ValidationCode;
import co.edu.uniquindio.social_reports.repositories.UserRepository;
import co.edu.uniquindio.social_reports.services.interfaces.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String userId = "67fb3897397d860960faba23";

//  Antes de iniciar los test, debe cambiar la base de datos a la de pruebas. Ademas, asegurarse de que se carguen los registros del dataset.js

    @Test
    public void registerUserTest() throws Exception {

        RegisterUserDTO userDTO = new RegisterUserDTO(
                "Pepe el pruebas",
                City.BOGOTA,
                "Casa de pepe",
                "3225179118",
                "pepepistolas@gmail.com",
                "12345678"
        );

        assertDoesNotThrow(() -> service.registerUser(userDTO));

        User user = repository.findUserByEmail(userDTO.email()).get();

        assertNotNull(user);
        assertTrue(repository.existsById(user.getId()));
        assertTrue(passwordEncoder.matches(userDTO.password(), user.getPassword()));
        assertEquals(userDTO.name(), user.getName(), "El nombre no coincide");
        assertEquals(userDTO.email(), user.getEmail(), "El email no coincide");
        repository.deleteById(user.getId());
    }

    @Test
    public void updateUserTest() throws Exception {
        UpdateUserDTO dto = new UpdateUserDTO(
                "Pepesito El insano",
                City.ARMENIA,
                "Casa de Pepesito en Armenia",
                "3148901867"
        );

        ObjectId id = new ObjectId("67fb3897397d860960faba20");

        assertDoesNotThrow(() -> service.updateUser(id.toString(), dto));
        User user = repository.findById(id).get();
        assertNotNull(user);
        assertEquals(dto.name(), user.getName());
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = User.builder()
                .id(new ObjectId("66d082d1f1f27b1e5b8e1339"))
                .name("Pepe el eliminado")
                .email("arroa03@gmail.com")
                .password("pepesito123")
                .status(UserStatus.ACTIVE)
                .build();
        repository.save(user);

        assertDoesNotThrow(() -> service.deleteUser(user.getId().toString()));

        Optional<User> userDeleted = repository.findById(user.getId());
        assertTrue(userDeleted.isPresent());
        assertEquals(UserStatus.DELETED, userDeleted.get().getStatus());
        repository.deleteById(user.getId());
    }

    @Test
    public void getUserInfoTest() throws Exception {
        UserInfoDTO dto = service.getUserInfo(userId);

        assertNotNull(dto);
        assertEquals("Carlos Ramos", dto.name());
        assertEquals(City.PEREIRA.name(), dto.city());
        assertEquals("Diagonal 45 #78-56", dto.address());
        assertEquals("3152233445", dto.phone());
        assertEquals("carlos.ramos@example.com", dto.email());
    }

    @Test
    public void updatedPassword() {

        ChangePasswordDTO dto = new ChangePasswordDTO(
                "luisa.mendez@example.com",
                "LU1SA2",
                "pepeRenovado"
        );

        Optional<User> optionalUser = repository.findUserByEmail(dto.email());
        assertTrue(optionalUser.isPresent());

        optionalUser.get().setValidationCode(
                ValidationCode.builder()
                        .date(LocalDateTime.now())
                        .code("LU1SA2")
                        .build()
        );

        repository.save(optionalUser.get());

        assertDoesNotThrow(() -> service.changePassword(dto));

        Optional<User> userUpdated = repository.findUserByEmail(dto.email());
        assertTrue(userUpdated.isPresent());
        assertTrue(passwordEncoder.matches(dto.newPassword(), userUpdated.get().getPassword()));
    }

    @Test
    public void logInTest() throws Exception {
        User user = repository.findById(new ObjectId(userId)).get();
        assertNotNull(user);

        LogInDTO loginDTO = new LogInDTO(user.getEmail(), "12345678");
        TokenDTO tokenDTO = service.logIn(loginDTO);
        assertNotNull(tokenDTO.token(), "El token devuelto no debería ser nulo");
    }

    @Test
    public void getAllUsers() {
        User user1 = User.builder()
                .id(new ObjectId("66d082d1f1f27b1e5b8e1340"))
                .name("Pepe el probador")
                .email("pepin1@gmail.com")
                .password("pepesito123")
                .status(UserStatus.ACTIVE)
                .city(City.ARMENIA)
                .build();

        User user2 = User.builder()
                .id(new ObjectId("66d082d1f1f27b1e5b8e1341"))
                .name("Pepe el testeador")
                .email("pepin2@gmail.com")
                .password("pepesito123")
                .status(UserStatus.ACTIVE)
                .city(City.ARMENIA)
                .build();

        repository.saveAll(List.of(user1, user2));

        assertDoesNotThrow(() -> service.getAllUsers());
    }
}
