package lk.ijse.greenshadowbacend.Service.impl;

import lk.ijse.greenshadowbacend.Dto.impl.LogDto;
import lk.ijse.greenshadowbacend.Service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Override
    public LogDto save(LogDto dto) {
        return null;
    }

    @Override
    public LogDto update(String id, LogDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public LogDto findById(String id) {
        return null;
    }

    @Override
    public List<LogDto> findAll() {
        return null;
    }
}