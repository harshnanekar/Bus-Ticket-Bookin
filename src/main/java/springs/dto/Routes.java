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
@Table(name="routes")
@Data
public class Routes {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String route;

    @Column
    private String created_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_date;

    @Column
    private String modified_by ;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date modified_date ;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @OneToMany(mappedBy = "departure_location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Journey> departure_location = new ArrayList<>();

    @OneToMany(mappedBy = "arrival_location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Journey> arrival_location = new ArrayList<>();
    
}
