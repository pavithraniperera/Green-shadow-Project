package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.impl.UserDto;
import lk.ijse.greenshadowbacend.Secure.JWTAuthResponse;
import lk.ijse.greenshadowbacend.Secure.SignIn;


public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDto userDTO);
    JWTAuthResponse refreshToken(String accessToken);
}
