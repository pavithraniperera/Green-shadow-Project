package lk.ijse.greenshadowbacend.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Service.FieldService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/fields")
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

            // Save field
            fieldService.save(fieldDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Field created successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing images or field data");
        }
    }



}
