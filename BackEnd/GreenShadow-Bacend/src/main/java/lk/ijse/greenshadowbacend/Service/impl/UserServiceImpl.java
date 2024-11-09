package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.UserDao;
import lk.ijse.greenshadowbacend.Dto.UserStatus;
import lk.ijse.greenshadowbacend.Dto.impl.UserDto;
import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import lk.ijse.greenshadowbacend.Entity.UserEntity;
import lk.ijse.greenshadowbacend.Service.UserService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao ;
    @Autowired
    private Mapping userMapping;

    @Override
    public UserDto save(UserDto dto) {
        dto.setId(AppUtil.generateUserId());
        return userMapping.toUserDto(userDao.save(userMapping.toUserEntity(dto)));
    }

    @Override
    public UserDto update(String id, UserDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {
         userDao.deleteById(id);
    }

    @Override
    public UserDto findById(String id) {
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return userMapping.asUserDtoList(userDao.findAll());
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<UserEntity> byEmail = userDao.findByEmail(email);

        return byEmail.map(userMapping::toUserDto);
    }
}
