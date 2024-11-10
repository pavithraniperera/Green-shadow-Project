package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;

import java.util.List;

public interface FieldService extends BaseService<FieldDto> {
    List<StaffDto> getStaffIdsByFieldId(String fieldId);

}
