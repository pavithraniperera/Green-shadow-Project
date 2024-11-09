package lk.ijse.greenshadowbacend.Dto.impl;

import lk.ijse.greenshadowbacend.Dto.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class FieldDto implements FieldStatus {
    private String fieldId;
    private String name;
    private String location;
    private Double size;
    private String image1;
    private String image2;
   /* private Set<StaffDto> staffMembers; // Many-to-many relationship with Staff
    private Set<CropDto> crops; // One-to-many relationship with Crop*/

/* public FieldDto(String fieldId,String name,String location,Double size,String image1,String image2){
     this.fieldId = fieldId;
     this.name =name;
     this.location=location;
     this.size=size;
     this.image1=image1;
     this.image2=image2;

 }*/
}
