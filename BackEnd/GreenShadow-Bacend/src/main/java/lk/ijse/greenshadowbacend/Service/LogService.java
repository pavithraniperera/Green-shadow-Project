package lk.ijse.greenshadowbacend.Service;

import lk.ijse.greenshadowbacend.Dto.impl.LogDto;

import java.util.Map;

public interface LogService extends BaseService<LogDto> {
    Map<String, Object> getRelatedEntitiesAsDtos(String logId);
}
