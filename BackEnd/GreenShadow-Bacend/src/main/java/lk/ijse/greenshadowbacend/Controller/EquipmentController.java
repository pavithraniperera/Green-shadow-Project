package lk.ijse.greenshadowbacend.Controller;

import lk.ijse.greenshadowbacend.Dto.impl.EquipmentDto;
import lk.ijse.greenshadowbacend.Dto.impl.FieldDto;
import lk.ijse.greenshadowbacend.Exception.EquipmentNotFoundException;
import lk.ijse.greenshadowbacend.Exception.VehicleNotFoundException;
import lk.ijse.greenshadowbacend.Service.EquipmentService;
import lk.ijse.greenshadowbacend.Util.RegexUtilForId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipments")
@CrossOrigin

public class EquipmentController {
    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);
    @Autowired
    private EquipmentService equipmentService;
    @PostMapping
    public ResponseEntity<EquipmentDto> saveEquipment(@RequestBody EquipmentDto equipmentDto) {
        logger.info("Request to save equipment: {}", equipmentDto);
        EquipmentDto createdEquipment = equipmentService.save(equipmentDto);
        logger.info("Equipment saved successfully: {}", createdEquipment);
        return new ResponseEntity<>(createdEquipment, HttpStatus.CREATED);
    }
    @PutMapping("/{equipmentId}")
    public ResponseEntity<EquipmentDto> updateEquipment(@PathVariable String equipmentId, @RequestBody EquipmentDto equipmentDto) {
        logger.info("Request to update equipment with ID: {}, data: {}", equipmentId, equipmentDto);
        EquipmentDto updatedEquipment = equipmentService.update(equipmentId, equipmentDto);
        return ResponseEntity.ok(updatedEquipment);
    }
    @DeleteMapping("/{equipmentId}")
    public ResponseEntity<String> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        logger.info("Request to delete equipment with ID: {}", equipmentId);
        try{
            if (!RegexUtilForId.isValidEquipmentId(equipmentId)){
                logger.warn("Invalid equipment ID format: {}", equipmentId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                equipmentService.delete(equipmentId);
                return new ResponseEntity<>("Equipment deleted successfully.", HttpStatus.NO_CONTENT);
            }
        }catch (EquipmentNotFoundException e){
            logger.error("Equipment not found with ID: {}", equipmentId, e);
            return new ResponseEntity<>("Equipment not found.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping("/{equipmentId}")
    public ResponseEntity<EquipmentDto> getEquipmentById(@PathVariable String equipmentId) {
        logger.info("Request to fetch equipment with ID: {}", equipmentId);
        EquipmentDto equipment = equipmentService.findById(equipmentId);
        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<EquipmentDto>> getEquipmentByStaffId(@PathVariable String staffId) {
        logger.info("Request to fetch equipment assigned to staff ID: {}", staffId);

        List<EquipmentDto> equipmentList = equipmentService.getEquipmentByStaffId(staffId);
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<EquipmentDto>> getEquipmentByFieldId(@PathVariable String fieldId) {
        logger.info("Request to fetch equipment assigned to field ID: {}", fieldId);
        List<EquipmentDto> equipmentList = equipmentService.getEquipmentByFieldId(fieldId);
        return ResponseEntity.ok(equipmentList);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDto> getAllUsers(){
        logger.info("Request to fetch all equipment");
        return equipmentService.findAll();
    }
}
