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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="modules")
@Data
public class Modules {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String modules;

    @ManyToOne
    @JoinColumn(name = "parent_lid")
    private Modules parent_lid;

    @Column
    private String icon;

    @Column
    private String created_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_date;

    @Column
    private String modified_by ;

    @Column
    private String url;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date modified_date ;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @OneToMany(mappedBy = "modules_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserModules> userModules = new ArrayList<>();
    
}
