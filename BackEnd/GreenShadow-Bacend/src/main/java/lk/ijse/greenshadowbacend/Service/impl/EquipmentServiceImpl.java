package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.EquipmentDao;
import lk.ijse.greenshadowbacend.Dao.FieldDao;
import lk.ijse.greenshadowbacend.Dao.StaffDao;
import lk.ijse.greenshadowbacend.Dto.impl.EquipmentDto;
import lk.ijse.greenshadowbacend.Entity.EquipmentEntity;
import lk.ijse.greenshadowbacend.Entity.FieldEntity;
import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import lk.ijse.greenshadowbacend.Service.EquipmentService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping equipmentMapper;
    @Override
    public EquipmentDto save(EquipmentDto dto) {
        dto.setEquipmentId(AppUtil.generateEquipmentId());
        EquipmentEntity equipment = equipmentMapper.toEquipmentEntity(dto);
        equipment = equipmentDao.save(equipment);
        return equipmentMapper.toEquipmentDto(equipment);

    }

    @Override
    public EquipmentDto update(String id, EquipmentDto dto) {
        EquipmentEntity equipment = equipmentDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with ID: " + id));

        equipment.setType(dto.getType());
        equipment.setName(dto.getName());
        equipment.setStatus(dto.getStatus());

        if (dto.getFieldId() != null) {
            FieldEntity field = fieldDao.findById(dto.getFieldId())
                    .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + dto.getFieldId()));
            equipment.setField(field);
        }

        if (dto.getStaffId() != null) {
            StaffEntity staff = staffDao.findById(dto.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + dto.getStaffId()));
            equipment.setStaff(staff);
        }

        return equipmentMapper.toEquipmentDto(equipmentDao.save(equipment));
    }

    @Override
    public void delete(String id) {
      equipmentDao.deleteById(id);
    }

    @Override
    public EquipmentDto findById(String id) {
        EquipmentEntity equipment = equipmentDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with ID: " + id));
        return equipmentMapper.toEquipmentDto(equipment);
    }

    @Override
    public List<EquipmentDto> findAll() {
        return equipmentMapper.asEquipmentDtoList(equipmentDao.findAll());
    }
    public List<EquipmentDto> getEquipmentByStaffId(String staffId) {
        StaffEntity staff = staffDao.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId));
        List<EquipmentEntity> equipmentList = equipmentDao.findByStaff(staff);
        return equipmentMapper.asEquipmentDtoList(equipmentList);
    }
    public List<EquipmentDto> getEquipmentByFieldId(String fieldId) {
        FieldEntity field = fieldDao.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldId));
        List<EquipmentEntity> equipmentList = equipmentDao.findByField(field);
        return equipmentMapper.asEquipmentDtoList(equipmentList);
    }
}
