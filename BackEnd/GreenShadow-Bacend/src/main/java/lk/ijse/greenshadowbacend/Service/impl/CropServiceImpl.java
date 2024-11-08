package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dto.impl.CropDto;
import lk.ijse.greenshadowbacend.Service.CropService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CropServiceImpl implements CropService {
    @Override
    public CropDto save(CropDto dto) {
        return null;
    }

    @Override
    public CropDto update(String id, CropDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CropDto findById(String id) {
        return null;
    }

    @Override
    public List<CropDto> findAll() {
        return null;
    }
}
