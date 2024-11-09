package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.FieldDao;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Entity.FieldEntity;
import lk.ijse.greenshadowbacend.Service.FieldService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping fieldMapping;
    @Override
    public FieldDto save(FieldDto dto) {
        dto.setFieldId(AppUtil.generateFieldId());
       return fieldMapping.toFieldDto(fieldDao.save(fieldMapping.toFieldEntity(dto)));
    }

    @Override
    public FieldDto update(String id, FieldDto dto) {
        FieldEntity existingField = fieldDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + id));

        // Update basic field properties
        existingField.setName(dto.getName());
        existingField.setSize(dto.getSize());
        existingField.setLocation(dto.getLocation());


        // Handle images if provided
        if (dto.getImage1() != null) {
            existingField.setImage1(dto.getImage1());
        }

        if (dto.getImage2() != null) {
            existingField.setImage2(dto.getImage2());
        }

        // Save the updated field entity
        return fieldMapping.toFieldDto(fieldDao.save(existingField));

    }

    @Override
    public void delete(String id) {
        fieldDao.deleteById(id);

    }

    @Override
    public FieldDto findById(String id) {
        Optional<FieldEntity> byId = fieldDao.findById(id);
        if (byId.isPresent()){
            return fieldMapping.toFieldDto(byId.get());
        }
        return null;
    }

    @Override
    public List<FieldDto> findAll() {
        return fieldMapping.asFieldDtoList(fieldDao.findAll());
    }



}
