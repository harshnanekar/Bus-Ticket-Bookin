package springs.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bus_master")
@Data
public class BusMaster {

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
    private int capacity;

    @Column
    private int seats_per_row;

    @Column
    private int total_rows;

    @Column
    private boolean last_row;

    public boolean getLast_row() {
        return last_row;
    }

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

    @Transient
    private int bus_type_id;

    @OneToMany(mappedBy = "bus_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seats> bus_lid = new ArrayList<>();

}
