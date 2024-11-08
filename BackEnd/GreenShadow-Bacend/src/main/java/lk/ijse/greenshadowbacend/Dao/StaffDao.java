package lk.ijse.greenshadowbacend.Dao;

import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<StaffEntity,String> {
}
