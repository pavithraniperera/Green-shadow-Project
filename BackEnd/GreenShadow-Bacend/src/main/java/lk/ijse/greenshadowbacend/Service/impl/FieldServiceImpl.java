package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.FieldDao;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Service.FieldService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
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
return  null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public FieldDto findById(String id) {
        return null;
    }

    @Override
    public List<FieldDto> findAll() {
        return null;
    }



}
