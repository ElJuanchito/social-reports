package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.email.EmailDTO;
import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.user.*;
import co.edu.uniquindio.social_reports.exceptions.user.*;
import co.edu.uniquindio.social_reports.model.entities.User;
import co.edu.uniquindio.social_reports.model.enums.Role;
import co.edu.uniquindio.social_reports.model.enums.UserStatus;
import co.edu.uniquindio.social_reports.model.vo.ValidationCode;
import co.edu.uniquindio.social_reports.repositories.UserRepository;
import co.edu.uniquindio.social_reports.security.JWTUtils;
import co.edu.uniquindio.social_reports.services.interfaces.EmailService;
import co.edu.uniquindio.social_reports.services.interfaces.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    public void registerUser(RegisterUserDTO userDTO) throws Exception {

        if(userRepository.findUserByEmail(userDTO.email()).isPresent()) {
            throw new EmailExistsException("El usuario con email:" + userDTO.email() + ", ya existe");
        }

        User user = dtoToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setRole(Role.CLIENT);
        user.setStatus(UserStatus.INACTIVE);
        ValidationCode validationCode = ValidationCode
                .builder()
                .code(generateCode())
                .date(LocalDateTime.now())
                .build();
        user.setValidationCode(validationCode);
        user = userRepository.save(user);

        sendRegistrationCode(user.getEmail(), user.getValidationCode().getCode());
    }

    @Override
    public void updateUser(String id, UpdateUserDTO userDTO) throws UserNotExistsException {
        User user = getUserById(id);
        user.setName(userDTO.name());
        user.setAddress(userDTO.address());
        user.setPhone(userDTO.phone());
        user.setCity(userDTO.city());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) throws UserNotExistsException {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);

        userRepository.save(user);
    }

    @Override
    public UserInfoDTO getUserInfo(String id) throws UserNotExistsException {
        User user = getUserById(id);

        return entityToInfoDTO(user);
    }

    @Override
    public void sendPasswordResetCode(String email) throws Exception {
        User user = getUserByEmail(email);

        ValidationCode validationCode = ValidationCode
                .builder()
                .code(generateCode())
                .date(LocalDateTime.now())
                .build();
        user.setValidationCode(validationCode);
        userRepository.save(user);

        String subject = "Password reset code";
        emailService.sendEmail(new EmailDTO(subject, validationCode.getCode(), email));
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        User user = getEmailByEmail(changePasswordDTO.email());

        ValidationCode validationCode = user.getValidationCode();

        if(changePasswordDTO.recoverCode() != null) {
            if(validationCode.getCode().equals(changePasswordDTO.recoverCode())) {

                if(validationCode.getDate().plusMinutes(15).isAfter(LocalDateTime.now())) {
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
                    userRepository.save(user);
                } else {
                    user.setValidationCode(null);
                    userRepository.save(user);
                    throw new CodeExpiredException("Su codigo de verificacion ya expiro");
                }
            }else throw new WrongCodeException("El codigo no es correcto");
        }
    }

    @Override
    public TokenDTO logIn(LogInDTO logInDTO) throws Exception {
        User user = getUserByEmail(logInDTO.email());

        if(!passwordEncoder.matches(logInDTO.password(), user.getPassword())) throw new WrongPasswordException("La contrasena es incorrecta");

        String token = jwtUtils.generateToken(user.getId().toString(), createClaims(user));
        return new TokenDTO(token);
    }

    @Override
    public void resendActivationCode(String email) throws Exception {
        User user = getUserByEmail(email);
        if(user.getStatus() == UserStatus.ACTIVE) throw new UserAlreadyActiveException("El usuario ya esta activo");
        ValidationCode validationCode = ValidationCode
                .builder()
                .code(generateCode())
                .date(LocalDateTime.now())
                .build();
        user.setValidationCode(validationCode);
        user = userRepository.save(user);

        String subject = "Resend activation code";
        emailService.sendEmail(new EmailDTO(subject, validationCode.getCode(), email));


    }

    @Override
    public List<UserInfoDTO> getAllUsers() throws Exception {
        return userRepository.findAll()
                .stream()
                .map(this::entityToInfoDTO)
                .toList();
    }

    @Override
    public void activateUser(String email, String code) throws Exception {
        User user = getEmailByEmail(email);

        ValidationCode validationCode = user.getValidationCode();

        if(code != null) {
            if(validationCode.getCode().equals(code)) {

                if(validationCode.getDate().plusMinutes(15).isAfter(LocalDateTime.now())) {
                    user.setStatus(UserStatus.ACTIVE);
                    userRepository.save(user);
                } else {
                    user.setValidationCode(null);
                    userRepository.save(user);
                    throw new CodeExpiredException("Su codigo de verificacion ya expiro");
                }
            }else throw new WrongCodeException("El codigo no es correcto");
        }
    }


    private User getEmailByEmail(String email) throws EmailNotExistsException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isEmpty()) throw new EmailNotExistsException("El email no esta registrado");
        return optionalUser.get();
    }

    private User dtoToEntity(RegisterUserDTO dto) {
        ValidationCode code = ValidationCode.builder()
                .code(generateCode())
                .date(LocalDateTime.now())
                .build();

        return User.builder()
                .name(dto.name())
                .city(dto.city())
                .address(dto.address())
                .phone(dto.phone())
                .email(dto.email())
                .validationCode(code)
                .build();
    }

    private User getUserById(@NotNull String id) throws UserNotExistsException {
        Optional<User> optionalUser = userRepository.findById(new ObjectId(id));

        if(optionalUser.isEmpty()) throw new UserNotExistsException("El usuario con el id " + id + "no existe" );
        return optionalUser.get();
    }

    private User getUserByEmail(@NotNull String email) throws UserNotExistsException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if(optionalUser.isEmpty()) throw new UserNotExistsException("El usuario con email " + email + "no existe" );
        return optionalUser.get();
    }

    private String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    private UserInfoDTO entityToInfoDTO(User user) {
        return new UserInfoDTO(user.getName(), user.getCity().name(),
                user.getAddress(),
                user.getPhone(),
                user.getEmail()
        );
    }

    private void sendRegistrationCode(String email, String code) throws Exception {
        String subject = "Registration confirmation code";
        emailService.sendEmail(new EmailDTO(subject, code, email));
    }

    private Map<String, String> createClaims(User user){
        return Map.of(
                "email", user.getEmail(),
                "nombre", user.getName(),
                "rol", "ROLE_"+user.getRole().name()
        );
    }
}

