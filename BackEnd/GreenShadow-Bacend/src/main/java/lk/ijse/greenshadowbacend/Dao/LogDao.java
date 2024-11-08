package lk.ijse.greenshadowbacend.Dao;

import lk.ijse.greenshadowbacend.Entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDao extends JpaRepository<LogEntity,String> {
}
