package springs.dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "journey_details")
@Data
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bus_lid")
    private BusMaster bus_lid;

    @Column
    private Time departure_time;

    @ManyToOne
    @JoinColumn(name = "departure_location")
    private Routes departure_location;

    @Column
    private Time arrival_time;

    @ManyToOne
    @JoinColumn(name = "arrival_location")
    private Routes arrival_location;

    @Column
    private int fare;

    @Column
    private String travel_time;

    @Column
    private Date journey_date;

    @Column
    private String created_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_date;

    @Column
    private String modified_by;

    @Transient
    private String busName;

    @Transient
    private String arrivalLocation;

    @Transient
    private String departureLocation;

    @Transient
    private int busId;

    @Transient
    private int departureId;

    @Transient
    private int arrivalId;

    @Transient
    private List<BoardingPoints> boardingPoints;

    @Transient
    private List<DroppingPoints> droppingPoints;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date modified_date;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @OneToMany(mappedBy = "journey_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardingPoints> journey_boarding_lid = new ArrayList<>();

    @OneToMany(mappedBy = "journey_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DroppingPoints> journey_dropping_lid = new ArrayList<>();

}
