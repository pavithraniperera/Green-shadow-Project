package lk.ijse.greenshadowbacend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Dto.impl.LogDto;
import lk.ijse.greenshadowbacend.Exception.FieldNotFoundException;
import lk.ijse.greenshadowbacend.Service.LogService;
import lk.ijse.greenshadowbacend.Util.AppUtil;
import lk.ijse.greenshadowbacend.Util.RegexUtilForId;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/logs")
@CrossOrigin
public class LogController {
    @Autowired
    private LogService logService;
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogDto> saveLog(@RequestParam("logData") String logData,
                                          @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        log.info("Received request to save log with data: {}", logData);
        try {
            // Convert JSON string to LogDto object
            ObjectMapper objectMapper = new ObjectMapper();
            LogDto dto = objectMapper.readValue(logData, LogDto.class);
            System.out.println(logData);

            // Convert image to Base64 if provided
            if (imageFile != null) {
                dto.setImage2(AppUtil.imageToBase64(imageFile.getBytes()));
                log.debug("Image successfully converted to Base64");
            }

            // Save the log and return the response
            LogDto savedLog = logService.save(dto);
            log.info("Log saved successfully with ID: {}", savedLog.getLogId());
            return new ResponseEntity<>(savedLog, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error saving log: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

        // Update log
        @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<String> updateField(
                @PathVariable("id") String logId,
                @RequestParam("logData") String logData,
                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile)
               {

            try {
                log.info("Received request to update log with ID: {}", logId);
                // Convert fieldData JSON string to FieldDto object
                ObjectMapper objectMapper = new ObjectMapper();
                LogDto logDto = objectMapper.readValue(logData, LogDto.class);

                // Convert images to Base64 if provided and set them in the DTO
                if (imageFile != null && !imageFile.isEmpty()) {
                    logDto.setImage2(AppUtil.imageToBase64(imageFile.getBytes()));
                    log.debug("Updated image successfully converted to Base64");
                }


                // Call the service to update the field
                logService.update(logId, logDto);
                log.info("Log updated successfully with ID: {}", logId);

                return ResponseEntity.status(HttpStatus.OK).body("Log updated successfully");
            } catch (Exception e) {
                log.error("Error updating log with ID {}: {}", logId, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Log: " + e.getMessage());
            }
        }

    @DeleteMapping("/{logId}")
    public ResponseEntity<String> deleteField(@PathVariable("logId") String logId) {
        try{
            if (!RegexUtilForId.isValidLogId(logId)){
                log.info("Received request to delete log with ID: {}", logId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                logService.delete(logId);
                log.warn("Invalid log ID format: {}", logId);
                return new ResponseEntity<>("Log deleted successfully.", HttpStatus.NO_CONTENT);
            }
        }catch (FieldNotFoundException e){
            log.error("Log not found with ID: {}", logId, e);
            return new ResponseEntity<>("Log not found.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error deleting log with ID {}: {}", logId, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogDto> getAllUsers(){
        return logService.findAll();
    }

    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLogById(@PathVariable("logId") String logId) {
        // Validate field ID format using RegexUtilForId
        log.info("Received request to retrieve all logs");
        if (!RegexUtilForId.isValidLogId(logId)) {
            log.info("Received request to retrieve log by ID: {}",logId );
            return new ResponseEntity<>( "Log ID format is invalid", HttpStatus.BAD_REQUEST);
        }

        // Retrieve the field
        LogDto logDto = logService.findById(logId);
        if (logDto == null) {
            return new ResponseEntity<>( "Log not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(logDto, HttpStatus.OK);
    }

    @GetMapping("/{logId}/related-entities")
    public ResponseEntity<Map<String, Object>> getRelatedEntities(@PathVariable String logId) {
        log.info("Received request to retrieve related entities for log ID: {}", logId);
        Map<String, Object> relatedEntities = logService.getRelatedEntitiesAsDtos(logId);
        return ResponseEntity.ok(relatedEntities);
    }

}


