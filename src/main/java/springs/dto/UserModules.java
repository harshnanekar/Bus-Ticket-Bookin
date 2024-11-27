package springs.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user_modules")
@Data
public class UserModules {
    
 
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_lid")
    private User user_lid; 

    @ManyToOne
    @JoinColumn(name = "role_lid")
    private Roles role_lid;
    
    @ManyToOne
    @JoinColumn(name = "modules_lid")
    private Modules modules_lid;

    @Column
    private String created_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_date;

    @Column
    private String modified_by ;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date modified_date;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;


}
