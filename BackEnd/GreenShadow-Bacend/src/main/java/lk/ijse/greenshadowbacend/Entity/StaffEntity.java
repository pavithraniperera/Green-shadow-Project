package lk.ijse.greenshadowbacend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "staff")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffEntity {
    @Id
    private String staffId;
    private String firstName;
    private String lastName;
    private String gender;
    private String designation;
    @Column(unique = true)
    private String email;
    private Date dob;
    private String address;
    private String contact;
    private Date joinDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "staff",cascade = CascadeType.ALL, orphanRemoval = true)
    private UserEntity user;



    @ManyToMany
    @JoinTable(
            name = "staff_fields_detail",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    @JsonManagedReference // Manage the serialization
    private List<FieldEntity> fields;
    @ManyToMany(mappedBy = "staffLogs",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<LogEntity> logs;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<VehicleEntity> vehicles;
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipmentEntity> equipment;


}
