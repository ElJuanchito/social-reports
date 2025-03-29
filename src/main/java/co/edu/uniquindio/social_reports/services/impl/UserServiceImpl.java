package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.user.*;
import co.edu.uniquindio.social_reports.repositories.UserRepository;
import co.edu.uniquindio.social_reports.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void registerUser(RegisterUserDTO userDTO) throws Exception {
        userRepository.existsByEmailIgnoreCase(userDTO.email());
    }

    @Override
    public void updateUser(String id, UpdateUserDTO userDTO) throws Exception {
        //TODO
    }

    @Override
    public void deleteUser(String id) throws Exception {
        //TODO
    }

    @Override
    public UserInfoDTO getUserInfo(String id) throws Exception {
        return null;
        //TODO
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
        return List.of();
    }

    @Override
    public void activateUser(String id, String code) throws Exception {

    }
}

