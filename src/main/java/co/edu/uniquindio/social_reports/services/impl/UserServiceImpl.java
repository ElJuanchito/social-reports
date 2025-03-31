package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.user.*;
import co.edu.uniquindio.social_reports.exceptions.*;
import co.edu.uniquindio.social_reports.model.entities.User;
import co.edu.uniquindio.social_reports.model.enums.UserStatus;
import co.edu.uniquindio.social_reports.model.vo.ValidationCode;
import co.edu.uniquindio.social_reports.repositories.UserRepository;
import co.edu.uniquindio.social_reports.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void registerUser(RegisterUserDTO userDTO) throws EmailExistsException {

        if(userRepository.findUserByEmail(userDTO.email()).isPresent()) {
            throw new EmailExistsException("El usuario con email:" + userDTO.email() + ", ya existe");
        }

        User user = dtoToEntity(userDTO);
        user = userRepository.save(user);

        //sendRegistrationCode(user.getEmail(), user.getValidationCode().getCode());
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
        //TODO
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        //TODO
    }

    @Override
    public TokenDTO logIn(LogInDTO logInDTO) throws Exception {
        return null;
        //TODO
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
                .password(dto.password())
                .validationCode(code)
                .build();
    }

    private User getUserById(@NotNull String id) throws UserNotExistsException {
        Optional<User> optionalUser = userRepository.findById(new ObjectId(id));

        if(optionalUser.isEmpty()) throw new UserNotExistsException("El usuario con el id " + id + "no existe" );
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
}

