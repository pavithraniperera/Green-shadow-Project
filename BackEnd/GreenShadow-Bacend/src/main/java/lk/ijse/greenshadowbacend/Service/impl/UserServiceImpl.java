package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dto.UserStatus;
import lk.ijse.greenshadowbacend.Dto.impl.UserDto;
import lk.ijse.greenshadowbacend.Service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public UserDto save(UserDto dto) {
        return null;
    }

    @Override
    public UserDto update(String id, UserDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public UserDto findById(String id) {
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }
}
