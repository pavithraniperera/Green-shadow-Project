package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.impl.EquipmentDto;

import java.util.List;

public interface EquipmentService extends BaseService<EquipmentDto> {
    List<EquipmentDto> getEquipmentByStaffId(String staffId);
    List<EquipmentDto> getEquipmentByFieldId(String fieldId);

}
