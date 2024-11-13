package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.UserStatus;
import lk.ijse.greenshadowbacend.Dto.impl.UserDto;
import lk.ijse.greenshadowbacend.Entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<UserDto>{
    Optional<UserDto> findByEmail(String email);

    UserDetailsService userDetailService();
}
