package lk.ijse.greenshadowbacend.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lk.ijse.greenshadowbacend.Entity.Role;
import lk.ijse.greenshadowbacend.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private String staffId;
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
    private String address;
    private String contact;
    private Date joinDate;
    private Role role;
}
