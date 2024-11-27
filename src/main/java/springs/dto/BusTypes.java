package springs.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bus_types")
@Data
public class BusTypes {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String bus_type;

    @Column
    private String created_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_date;

    @Column
    private String modified_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date modified_date;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @OneToMany(mappedBy = "bus_type_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusMaster> bus_type_lid = new ArrayList<>();

}
