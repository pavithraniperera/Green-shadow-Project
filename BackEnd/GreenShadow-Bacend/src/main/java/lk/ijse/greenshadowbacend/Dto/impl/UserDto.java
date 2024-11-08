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
    private String username;
    private String email;
    private Role role;
    private String staffId;

}
