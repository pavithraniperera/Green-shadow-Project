package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.CropDao;
import lk.ijse.greenshadowbacend.Dto.impl.CropDto;
import lk.ijse.greenshadowbacend.Service.CropService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CropServiceImpl implements CropService {
    @Autowired
    private CropDao cropDao;
    @Autowired
    private Mapping cropMapping;
    @Override
    public CropDto save(CropDto dto) {
        dto.setId(AppUtil.generateCropId());

        return cropMapping.toCropDto(cropDao.save(cropMapping.toCropEntity(dto)));
    }

    @Override
    public CropDto update(String id, CropDto dto) {
         return  null;
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
