package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dto.impl.VehicleDto;
import lk.ijse.greenshadowbacend.Service.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Override
    public VehicleDto save(VehicleDto dto) {
        return null;
    }

    @Override
    public VehicleDto update(String id, VehicleDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public VehicleDto findById(String id) {
        return null;
    }

    @Override
    public List<VehicleDto> findAll() {
        return null;
    }
}
