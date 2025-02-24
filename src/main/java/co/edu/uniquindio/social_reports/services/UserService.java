package co.edu.uniquindio.social_reports.services;

import co.edu.uniquindio.social_reports.dtos.auth.TokenDTO;
import co.edu.uniquindio.social_reports.dtos.user.*;

public interface UserService {

    void registerUser(RegisterUserDTO userDTO) throws Exception;
    void updateUser(String id, UpdateUserDTO userDTO) throws Exception;
    void deleteUser(String id) throws Exception;
    UserInfoDTO getUserInfo(String id) throws Exception;
    void sendPasswordResetCode(String email) throws Exception;
    void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;
    TokenDTO logIn(LogInDTO logInDTO) throws Exception;
}
