package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.impl.VehicleDto;

import java.util.List;

public interface VehicleService extends BaseService<VehicleDto> {
    List<VehicleDto> getVehiclesByStaffId(String staffId);
}
