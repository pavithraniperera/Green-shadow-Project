package lk.ijse.greenshadowbacend.Controller;

import lk.ijse.greenshadowbacend.Dto.impl.VehicleDto;
import lk.ijse.greenshadowbacend.Exception.VehicleNotFoundException;
import lk.ijse.greenshadowbacend.Service.VehicleService;
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
@RequestMapping("api/v1/vehicles")
@CrossOrigin
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    // Save Vehicle
    @PostMapping
    public ResponseEntity<VehicleDto> saveVehicle(@RequestBody VehicleDto vehicleDto) {
        log.info("Request received to save vehicle: {}", vehicleDto);
        VehicleDto savedVehicle = vehicleService.save(vehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }
    // Update Vehicle
    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable String vehicleId, @RequestBody VehicleDto vehicleDto) {
        log.info("Request received to update vehicle with ID: {}, Data: {}", vehicleId, vehicleDto);
        VehicleDto updatedVehicle = vehicleService.update(vehicleId, vehicleDto);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("vehicleId") String vehicleId) {
        try{
            log.info("Request received to delete vehicle with ID: {}", vehicleId);
            if (!RegexUtilForId.isValidVehicleId(vehicleId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                vehicleService.delete(vehicleId);
                return new ResponseEntity<>("Vehicle deleted successfully.", HttpStatus.NO_CONTENT);
            }
        }catch (VehicleNotFoundException e){
            return new ResponseEntity<>("vehicle not found.", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error deleting vehicle with ID: {}", vehicleId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable String vehicleId) {
        VehicleDto vehicle = vehicleService.findById(vehicleId);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDto> getAllUsers(){
        log.info("Request received to retrieve all vehicles.");
        return vehicleService.findAll();
    }

    // Get Vehicles by Staff ID
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<VehicleDto>> getVehiclesByStaffId(@PathVariable String staffId) {
        List<VehicleDto> vehicles = vehicleService.getVehiclesByStaffId(staffId);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

}
