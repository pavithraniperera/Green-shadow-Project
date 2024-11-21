package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dao.CropDao;
import lk.ijse.greenshadowbacend.Dao.FieldDao;
import lk.ijse.greenshadowbacend.Dao.LogDao;
import lk.ijse.greenshadowbacend.Dao.StaffDao;
import lk.ijse.greenshadowbacend.Dto.impl.CropDto;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.LogDto;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;
import lk.ijse.greenshadowbacend.Entity.CropEntity;
import lk.ijse.greenshadowbacend.Entity.FieldEntity;
import lk.ijse.greenshadowbacend.Entity.LogEntity;
import lk.ijse.greenshadowbacend.Entity.StaffEntity;
import lk.ijse.greenshadowbacend.Exception.LogNotFoundException;
import lk.ijse.greenshadowbacend.Service.LogService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.Mapping;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private CropDao cropDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping logMapping;
    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public LogDto save(LogDto dto) {
        dto.setLogId(AppUtil.generateLogId());
        LogEntity logEntity = logMapping.toLogEntity(dto);
        // Retrieve and set associated staff entities
        if (dto.getStaffIds() != null && !dto.getStaffIds().isEmpty()) {
            Set<StaffEntity> staffEntities = new HashSet<>(staffDao.findAllById(dto.getStaffIds()));
            if (staffEntities.size() != dto.getStaffIds().size()) {
                throw new IllegalArgumentException("One or more staff IDs are invalid.");
            }
            logEntity.setStaffLogs(staffEntities);
        }
        // Retrieve and set associated field entities
        if (dto.getFieldIds() != null && !dto.getFieldIds().isEmpty()) {
            Set<FieldEntity> fieldEntities = new HashSet<>(fieldDao.findAllById(dto.getFieldIds()));
            if (fieldEntities.size() != dto.getFieldIds().size()) {
                throw new IllegalArgumentException("One or more field IDs are invalid.");
            }
            logEntity.setFieldLogs(fieldEntities);
        }
        // Retrieve and set associated crop entities
        if (dto.getCropIds() != null && !dto.getCropIds().isEmpty()) {
            Set<CropEntity> cropEntities = new HashSet<>(cropDao.findAllById(dto.getCropIds()));
            if (cropEntities.size() != dto.getCropIds().size()) {
                throw new IllegalArgumentException("One or more crop IDs are invalid.");
            }
            logEntity.setCropLogs(cropEntities);
        }
        // Save the log entity and map back to DTO
        LogEntity savedLog = logDao.save(logEntity);
        return logMapping.toLogDto(savedLog);
    }

    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public LogDto update(String id, LogDto dto) {
        LogEntity existingLog = logDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Log not found with ID: " + id));

        // Update basic fields
        existingLog.setLogDetails(dto.getLogDetails());
        existingLog.setDate( dto.getDate());

        // Handle image update if provided
        if (dto.getImage2() != null) {
            existingLog.setImage2(dto.getImage2());
        }

       /* // Update associated staff
        if (dto.getStaffIds() != null && !dto.getStaffIds().isEmpty()) {
            List<StaffEntity> staffEntities = staffDao.findAllById(dto.getStaffIds());
            if (staffEntities.size() != dto.getStaffIds().size()) {
                throw new IllegalArgumentException("One or more staff IDs are invalid.");
            }
            existingLog.setStaffLogs(new HashSet<>(staffEntities));
        }

        // Update associated fields
        if (dto.getFieldIds() != null && !dto.getFieldIds().isEmpty()) {
            List<FieldEntity> fieldEntities = fieldDao.findAllById(dto.getFieldIds());
            if (fieldEntities.size() != dto.getFieldIds().size()) {
                throw new IllegalArgumentException("One or more field IDs are invalid.");
            }
            existingLog.setFieldLogs(new HashSet<>(fieldEntities));
        }

        // Update associated crops
        if (dto.getCropIds() != null && !dto.getCropIds().isEmpty()) {
            List<CropEntity> cropEntities = cropDao.findAllById(dto.getCropIds());
            if (cropEntities.size() != dto.getCropIds().size()) {
                throw new IllegalArgumentException("One or more crop IDs are invalid.");
            }
            existingLog.setCropLogs(new HashSet<>(cropEntities));
        }*/
        // Update associated staff
        if (dto.getStaffIds() != null && !dto.getStaffIds().isEmpty()) {
            updateStaffAssociations(existingLog, dto.getStaffIds());
        } else {
            existingLog.getStaffLogs().clear(); // Clear if no staff IDs are provided
        }

        // Update associated fields
        if (dto.getFieldIds() != null && !dto.getFieldIds().isEmpty()) {
            updateFieldAssociations(existingLog, dto.getFieldIds());
        } else {
            existingLog.getFieldLogs().clear(); // Clear if no field IDs are provided
        }

        // Update associated crops
        if (dto.getCropIds() != null && !dto.getCropIds().isEmpty()) {
            updateCropAssociations(existingLog, dto.getCropIds());
        } else {
            existingLog.getCropLogs().clear(); // Clear if no crop IDs are provided
        }


        // Save the updated log entity and return the updated DTO
        return logMapping.toLogDto(logDao.save(existingLog));
    }

    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    public void delete(String id) {
        logDao.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public LogDto findById(String id) {
        Optional<LogEntity> byId = logDao.findById(id);
        if (byId.isPresent()){
            return logMapping.toLogDto(byId.get());
        }
        return null;
    }

    @Override
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public List<LogDto> findAll() {
        return  logMapping.asLogDtoList(logDao.findAll());
    }

    private void updateStaffAssociations(LogEntity log, Set<String> staffIds) {
        List<StaffEntity> staffEntities = staffDao.findAllById(staffIds);
        if (staffEntities.size() != staffIds.size()) {
            throw new IllegalArgumentException("One or more staff IDs are invalid.");
        }

        // Add only new associations
        for (StaffEntity staff : staffEntities) {
            if (!log.getStaffLogs().contains(staff)) {
                log.getStaffLogs().add(staff);
                staff.getLogs().add(log); // Maintain bi-directional association
            }
        }

        // Remove unreferenced associations
        log.getStaffLogs().removeIf(staff -> !staffEntities.contains(staff));
    }

    private void updateFieldAssociations(LogEntity log, Set<String> fieldIds) {
        List<FieldEntity> fieldEntities = fieldDao.findAllById(fieldIds);
        if (fieldEntities.size() != fieldIds.size()) {
            throw new IllegalArgumentException("One or more field IDs are invalid.");
        }

        // Add only new associations
        for (FieldEntity field : fieldEntities) {
            if (!log.getFieldLogs().contains(field)) {
                log.getFieldLogs().add(field);
                field.getLogs().add(log); // Maintain bi-directional association
            }
        }

        // Remove unreferenced associations
        log.getFieldLogs().removeIf(field -> !fieldEntities.contains(field));
    }

    private void updateCropAssociations(LogEntity log, Set<String> cropIds) {
        List<CropEntity> cropEntities = cropDao.findAllById(cropIds);
        if (cropEntities.size() != cropIds.size()) {
            throw new IllegalArgumentException("One or more crop IDs are invalid.");
        }

        // Add only new associations
        for (CropEntity crop : cropEntities) {
            if (!log.getCropLogs().contains(crop)) {
                log.getCropLogs().add(crop);
                crop.getLogs().add(log); // Maintain bi-directional association
            }
        }

        // Remove unreferenced associations
        log.getCropLogs().removeIf(crop -> !cropEntities.contains(crop));
    }

    @Override
    public Map<String, Object> getRelatedEntitiesAsDtos(String logId) {
        Map<String, Object> relatedEntities = new HashMap<>();
        List<FieldDto> fieldDtos = null;
        List<CropDto> cropDtos=null;
        List<StaffDto> staffDtos =null;
        Optional<LogEntity> logEntity = logDao.findById(logId);
        if (logEntity.isPresent()){
            LogEntity log = logEntity.get();
            // Convert PersistentSet to List
            List<FieldEntity> fieldEntities = new ArrayList<>(log.getFieldLogs());
            List<CropEntity> cropEntities = new ArrayList<>(log.getCropLogs());
            List<StaffEntity> staffEntities = new ArrayList<>(log.getStaffLogs());
            if (!fieldEntities.isEmpty()){
                 fieldDtos =  logMapping.asFieldDtoList(fieldEntities);
            }
            if ((!cropEntities.isEmpty())){
                 cropDtos = logMapping.asCropDtoList( cropEntities);
            }
            if (!staffEntities.isEmpty()){
                staffDtos = logMapping.asStaffDtoList( staffEntities);
            }

        }
        relatedEntities.put("fields", fieldDtos);
        relatedEntities.put("crops", cropDtos);
        relatedEntities.put("staff", staffDtos);

       return relatedEntities;

    }
}

