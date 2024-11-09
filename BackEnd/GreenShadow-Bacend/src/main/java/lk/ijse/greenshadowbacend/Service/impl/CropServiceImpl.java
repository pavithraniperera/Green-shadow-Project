package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.CropDao;
import lk.ijse.greenshadowbacend.Dao.FieldDao;
import lk.ijse.greenshadowbacend.Dto.impl.CropDto;
import lk.ijse.greenshadowbacend.Entity.CropEntity;
import lk.ijse.greenshadowbacend.Entity.FieldEntity;
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
    private FieldDao fieldDao;
    @Autowired
    private Mapping cropMapping;
    @Override
    public CropDto save(CropDto dto) {
        dto.setId(AppUtil.generateCropId());

        return cropMapping.toCropDto(cropDao.save(cropMapping.toCropEntity(dto)));
    }

    @Override
    public CropDto update(String id, CropDto dto) {
        CropEntity existingCrop = cropDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Crop not found with ID: " + id));

        // Update basic crop properties
        existingCrop.setCommonName(dto.getCommonName());
        existingCrop.setSpecificName(dto.getSpecificName());
        existingCrop.setCategory(dto.getCategory());
        existingCrop.setSeason(dto.getSeason());

        // Set the field if provided in the DTO
        if (dto.getFieldId() != null) {
            FieldEntity field = fieldDao.findById(dto.getFieldId())
                    .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + dto.getFieldId()));
            existingCrop.setField(field);
        }

        // Handle images if provided
        if (dto.getImage1() != null) {
            existingCrop.setImage1(dto.getImage1());
        }

        // Save the updated crop entity
        return cropMapping.toCropDto(cropDao.save(existingCrop));
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
