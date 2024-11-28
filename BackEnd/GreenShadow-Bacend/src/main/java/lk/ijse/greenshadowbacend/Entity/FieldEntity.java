package lk.ijse.greenshadowbacend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "field")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldEntity {
    @Id
    private String FieldId;
    private String name;
    private String location;
    private Double size;
    @Column(columnDefinition = "LONGTEXT")
    private String image1;
    @Column(columnDefinition = "LONGTEXT")
    private String image2;

    @ManyToMany(mappedBy = "fields")
    @JsonBackReference
    private Set<StaffEntity> staffMembers = new HashSet<>();

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CropEntity> crops ;
    @ManyToMany(mappedBy = "fieldLogs",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<LogEntity> logs;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipmentEntity> equipment;



}
