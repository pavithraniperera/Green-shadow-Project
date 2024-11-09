package lk.ijse.greenshadowbacend.Controller;

import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;
import lk.ijse.greenshadowbacend.Dto.impl.UserDto;
import lk.ijse.greenshadowbacend.Service.StaffService;
import lk.ijse.greenshadowbacend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            // Check if a staff member exists with the given email
            Optional<StaffDto> existingStaff = staffService.findByEmail(userDto.getEmail());

            if (!existingStaff.isPresent()) {
                // Save new staff member if none exists
                StaffDto newStaff = new StaffDto();
                newStaff.setEmail(userDto.getEmail());
                newStaff.setRole(userDto.getRole());


                newStaff = staffService.save(newStaff);

                // Set the saved staff ID to the user DTO
                userDto.setStaffId(newStaff.getStaffId());
            } else {
                // Link to the existing staff member
                userDto.setStaffId(existingStaff.get().getStaffId());
            }

            // Save the user
            userService.save(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers(){
        return userService.findAll();
    }


}
