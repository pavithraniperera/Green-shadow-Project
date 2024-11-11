package lk.ijse.greenshadowbacend.Dao;

import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import lk.ijse.greenshadowbacend.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleDao extends JpaRepository<VehicleEntity,String> {
    // Custom method to find vehicles by Staff entity
    List<VehicleEntity> findByStaff(StaffEntity staff);
}
