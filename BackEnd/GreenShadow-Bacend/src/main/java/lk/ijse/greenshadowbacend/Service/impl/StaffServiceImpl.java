package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.StaffDao;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;
import lk.ijse.greenshadowbacend.Entity.CropEntity;
import lk.ijse.greenshadowbacend.Entity.FieldEntity;
import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import lk.ijse.greenshadowbacend.Service.StaffService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    @Autowired
    private Mapping staffMapper;
    @Override

    public StaffDto save(StaffDto dto) {
       dto.setStaffId(AppUtil.generateStaffId());
        StaffEntity save = staffDao.save(staffMapper.toStaffEntity(dto));
        if(save==null){
            System.out.println("not saved staff data");
            //throw new DataPersistException(" Staff not saved");
        }
        return staffMapper.toStaffDto(save);
    }

    @Override

    public StaffDto update(String id, StaffDto dto) {
        StaffEntity existingStaff = staffDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));

        // Update fields
        existingStaff.setFirstName(dto.getFirstName());
        existingStaff.setLastName(dto.getLastName());
        existingStaff.setEmail(dto.getEmail());
        existingStaff.setDob(dto.getDob());
        existingStaff.setAddress(dto.getAddress());
        existingStaff.setContact(dto.getContact());
        existingStaff.setJoinDate(dto.getJoinDate());
        existingStaff.setRole(dto.getRole());

        // Save updated entity
        StaffEntity updatedEntity = staffDao.save(existingStaff);

        // Convert updated entity back to DTO
        return staffMapper.toStaffDto(updatedEntity);
    }

    @Override

    public void delete(String id) {
        staffDao.deleteById(id);
    }

    @Override

    public StaffDto findById(String id) {
        Optional<StaffEntity> byId = staffDao.findById(id);
        if (byId.isPresent()){
            return staffMapper.toStaffDto(byId.get());
        }
        return null;
    }

    @Override

    public List<StaffDto> findAll() {
        return staffMapper.asStaffDtoList(staffDao.findAll());
    }

    @Override
    public Optional<StaffDto> findByEmail(String email) {
        Optional<StaffEntity> byEmail = staffDao.findByEmail(email);

        return byEmail.map(staffMapper::toStaffDto);

    }

    @Override

    public List<FieldDto> getFieldsOfStaffId(String staffId) {
        StaffEntity staff = staffDao.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId));

        return staffMapper.asFieldDtoList(new ArrayList<>(staff.getFields()));
    }
}
