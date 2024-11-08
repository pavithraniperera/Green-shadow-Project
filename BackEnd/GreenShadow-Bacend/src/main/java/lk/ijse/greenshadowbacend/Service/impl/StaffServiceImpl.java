package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.StaffDao;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;
import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import lk.ijse.greenshadowbacend.Service.StaffService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public StaffDto findById(String id) {
        return null;
    }

    @Override
    public List<StaffDto> findAll() {
        return null;
    }

    @Override
    public Optional<StaffDto> findByEmail(String email) {
        Optional<StaffEntity> byEmail = staffDao.findByEmail(email);

        return byEmail.map(staffMapper::toStaffDto);

    }
}
