package lk.ijse.greenshadowbacend.Dto.impl;

import lk.ijse.greenshadowbacend.Dto.LogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDto implements LogStatus {
    private String logId;
    private String logDetails;
    private Date date;
    private String image2;
    private String status;
    // IDs of associated entities for a lightweight representation
    private Set<String> staffIds;   // IDs of staff members monitoring this log
    private Set<String> fieldIds;     // IDs of fields related to this log
    private Set<String> cropIds;      // IDs of crops associated with this log

}
