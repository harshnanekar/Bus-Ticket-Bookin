package springs.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "bus_master")
@Data
public class BusMaster {

    public BusMaster(int id2, String busName, String busTypeName) {
        this.id = id2;
        this.bus_name = busName;
        this.bus_type = busTypeName;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String bus_name;

    @ManyToOne
    @JoinColumn(name = "bus_type_lid")
    @JsonIgnore
    private BusTypes bus_type_lid;

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

    @Transient
    private String bus_type;

}
