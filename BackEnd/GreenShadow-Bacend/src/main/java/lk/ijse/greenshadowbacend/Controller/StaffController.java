package lk.ijse.greenshadowbacend.Controller;

import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;
import lk.ijse.greenshadowbacend.Exception.FieldNotFoundException;
import lk.ijse.greenshadowbacend.Exception.StaffNotFoundException;
import lk.ijse.greenshadowbacend.Service.StaffService;
import lk.ijse.greenshadowbacend.Util.RegexUtilForId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/staffs")
@CrossOrigin
public class StaffController {
    private static final Logger log = LoggerFactory.getLogger(StaffController.class);
    @Autowired
    private StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<StaffDto> saveStaff(@RequestBody StaffDto staffDto) {
        log.info("Request received to save staff: {}", staffDto);
        StaffDto savedStaff = staffService.save(staffDto);
        log.info("Staff saved successfully: {}", savedStaff);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStaff);
    }

    @PutMapping(value = "/{staffId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable("staffId") String staffId, @RequestBody StaffDto staffDto) {
        log.info("Request received to update staff with ID: {}, Data: {}", staffId, staffDto);
        StaffDto updatedStaff = staffService.update(staffId, staffDto);
        log.info("Staff updated successfully: {}", updatedStaff);
        return ResponseEntity.ok(updatedStaff);
    }

    @DeleteMapping("/{staffId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<String> deleteStaff(@PathVariable("staffId") String staffId) {

        try{
            log.info("Request received to delete staff with ID: {}", staffId);
            if (!RegexUtilForId.isValidStaffId(staffId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                staffService.delete(staffId);
                return new ResponseEntity<>("Staff deleted successfully.", HttpStatus.NO_CONTENT);
            }
        }catch (StaffNotFoundException e){
            return new ResponseEntity<>("Staff not found.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error deleting staff with ID: {}", staffId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public List<StaffDto> getAllUsers(){
        log.info("Request received to retrieve all staff.");
        return staffService.findAll();

    }

    @GetMapping(value = "/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<?> getFieldById(@PathVariable("staffId") String staffId) {
        // Validate field ID format using RegexUtilForId

        if (!RegexUtilForId.isValidStaffId(staffId)) {
            return new ResponseEntity<>( "Staff ID format is invalid", HttpStatus.BAD_REQUEST);
        }

        // Retrieve the field
        StaffDto staffDto = staffService.findById(staffId);
        if (staffDto == null) {
            return new ResponseEntity<>( "Staff not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(staffDto, HttpStatus.OK);
    }

    @GetMapping("/{staffId}/field")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<List<FieldDto>> getFieldsOfStaffId(@PathVariable("staffId") String staffId) {
        log.info("Request received to retrieve fields for staff ID: {}", staffId);
        List<FieldDto> fieldDtos = staffService.getFieldsOfStaffId(staffId);
        return ResponseEntity.ok(fieldDtos);
    }
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATOR') or hasRole('SCIENTIST')")
    public ResponseEntity<StaffDto> getStaffByEmail(@PathVariable("email") String email) {
        log.info("Staff retrieved successfully by email: {}", email);
        Optional<StaffDto> staffDto = staffService.findByEmail(email);
        log.warn("Staff not found with email: {}", email);
        return ResponseEntity.ok(staffDto.get());
    }



}
