package lk.ijse.greenshadowbacend.Dao;

import lk.ijse.greenshadowbacend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity,String> {


}
