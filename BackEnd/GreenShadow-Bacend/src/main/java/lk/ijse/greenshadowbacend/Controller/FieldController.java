package lk.ijse.greenshadowbacend.Controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.StaffDto;
import lk.ijse.greenshadowbacend.Exception.FieldNotFoundException;
import lk.ijse.greenshadowbacend.Service.FieldService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.RegexUtilForId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/fields")
@CrossOrigin
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveField(
            @RequestParam("fieldData") String fieldData,
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2
    ) {
        try {
            System.out.println(fieldData);
            // Convert fieldData JSON string to FieldDto object
            ObjectMapper objectMapper = new ObjectMapper();
            FieldDto fieldDto = objectMapper.readValue(fieldData, FieldDto.class);


            // Convert images to Base64 and set in the FieldDto
            if (!image1.isEmpty()) {
                fieldDto.setImage1(AppUtil.imageToBase64(image1.getBytes()));
            }
            if (!image2.isEmpty()) {
                fieldDto.setImage2(AppUtil.imageToBase64(image2.getBytes()));
            }
            System.out.println(fieldData);

            // Save field
            fieldService.save(fieldDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Field created successfully");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing images or field data");
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateField(
            @PathVariable("id") String fieldId,
            @RequestParam("fieldData") String fieldData,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2) {

        try {
            // Convert fieldData JSON string to FieldDto object
            ObjectMapper objectMapper = new ObjectMapper();
            FieldDto fieldDto = objectMapper.readValue(fieldData, FieldDto.class);

            // Convert images to Base64 if provided and set them in the DTO
            if (image1 != null && !image1.isEmpty()) {
                fieldDto.setImage1(AppUtil.imageToBase64(image1.getBytes()));
            }
            if (image2 != null && !image2.isEmpty()) {
                fieldDto.setImage2(AppUtil.imageToBase64(image2.getBytes()));
            }

            // Call the service to update the field
            fieldService.update(fieldId, fieldDto);

            return ResponseEntity.status(HttpStatus.OK).body("Field updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating field: " + e.getMessage());
        }
    }


    @DeleteMapping("/{fieldId}")
    public ResponseEntity<String> deleteField(@PathVariable("fieldId") String fieldId) {
        try{
            if (!RegexUtilForId.isValidFieldId(fieldId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                fieldService.delete(fieldId);
                return new ResponseEntity<>("Field deleted successfully.", HttpStatus.NO_CONTENT);
            }
        }catch (FieldNotFoundException e){
            return new ResponseEntity<>("Field not found.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDto> getAllUsers(){
        return fieldService.findAll();
    }

    @GetMapping(value = "/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFieldById(@PathVariable("fieldId") String fieldId) {
        // Validate field ID format using RegexUtilForId
        if (!RegexUtilForId.isValidFieldId(fieldId)) {
            return new ResponseEntity<>( "Field ID format is invalid", HttpStatus.BAD_REQUEST);
        }

        // Retrieve the field
        FieldDto fieldDto = fieldService.findById(fieldId);
        if (fieldDto == null) {
            return new ResponseEntity<>( "Field not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fieldDto, HttpStatus.OK);
    }

    @GetMapping("/{fieldId}/staff")
    public ResponseEntity<List<StaffDto>> getStaffByFieldId(@PathVariable("fieldId") String fieldId) {
        List<StaffDto> staffList = fieldService.getStaffIdsByFieldId(fieldId);
        return ResponseEntity.ok(staffList);
    }




}
