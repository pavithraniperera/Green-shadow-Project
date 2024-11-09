package lk.ijse.greenshadowbacend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.greenshadowbacend.Dto.impl.CropDto;
import lk.ijse.greenshadowbacend.Service.CropService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.RegexUtilForId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/crops")
public class CropController {
    @Autowired
    private CropService cropService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveCrop(@RequestParam("cropData") String cropData,
                                      @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        // Convert cropData JSON string to CropDto object
        CropDto cropDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cropDto = objectMapper.readValue(cropData, CropDto.class);
            // Validate the field ID format
            if (!RegexUtilForId.isValidFieldId(cropDto.getFieldId())) {
                return new ResponseEntity<>("Invalid field ID format", HttpStatus.BAD_REQUEST);
            }

            // Convert the image file to Base64 string if provided
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    cropDto.setImage1(AppUtil.imageToBase64(imageFile.getBytes()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Save the crop
            CropDto savedCrop = cropService.save(cropDto);
            return new ResponseEntity<>(savedCrop, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid crop data", HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateCrop(
            @PathVariable("id") String cropId,
            @RequestParam("cropData") String cropData,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile)
             {

        try {
            if (!RegexUtilForId.isValidCropId(cropId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            // Convert fieldData JSON string to FieldDto object
            ObjectMapper objectMapper = new ObjectMapper();
            CropDto cropDto = objectMapper.readValue(cropData, CropDto.class);

            // Convert images to Base64 if provided and set them in the DTO
            if (imageFile != null && !imageFile.isEmpty()) {
                cropDto.setImage1(AppUtil.imageToBase64(imageFile.getBytes()));
            }


            // Call the service to update the field
            cropService.update(cropId, cropDto);

            return ResponseEntity.status(HttpStatus.OK).body("Field updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating field: " + e.getMessage());
        }
    }

}
