package lk.ijse.greenshadowbacend.Dto.impl;

import lk.ijse.greenshadowbacend.Dto.StaffStatus;
import lk.ijse.greenshadowbacend.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements StaffStatus {
    private String id;
    private String email;
    private String password;
    private Role role;
    private String staffId;

}
