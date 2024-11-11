package lk.ijse.greenshadowbacend.Dao;

import lk.ijse.greenshadowbacend.Entity.EquipmentEntity;
import lk.ijse.greenshadowbacend.Entity.FieldEntity;
import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentDao extends JpaRepository<EquipmentEntity,String> {
    List<EquipmentEntity> findByStaff(StaffEntity staff);
    List<EquipmentEntity> findByField(FieldEntity field);

}
