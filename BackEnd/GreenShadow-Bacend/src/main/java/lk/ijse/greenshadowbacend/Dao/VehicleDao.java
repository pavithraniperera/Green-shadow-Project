package lk.ijse.greenshadowbacend.Dao;

import lk.ijse.greenshadowbacend.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDao extends JpaRepository<VehicleEntity,String> {
}
