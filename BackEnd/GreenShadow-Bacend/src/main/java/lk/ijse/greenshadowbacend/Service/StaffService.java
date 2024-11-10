package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;

import java.util.List;
import java.util.Optional;

public interface StaffService extends BaseService<StaffDto> {

    Optional<StaffDto> findByEmail(String email);

    List<FieldDto> getFieldsOfStaffId(String staffId);
}
