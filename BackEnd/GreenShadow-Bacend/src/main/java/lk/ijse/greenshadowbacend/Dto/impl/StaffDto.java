package lk.ijse.greenshadowbacend.Dto.impl;

import lk.ijse.greenshadowbacend.Dto.StaffStatus;
import lk.ijse.greenshadowbacend.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto implements StaffStatus {
    private String staffId;
    private String firstName;
    private String lastName;
    private String gender;
    private String designation;
    private String email;
    private Date dob;
    private String address;
    private String contact;
    private Date joinDate;
    private Role role;
}
