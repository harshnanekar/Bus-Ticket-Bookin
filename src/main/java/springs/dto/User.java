package springs.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "user_info")
@Data
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String password;

    @Column(columnDefinition = "BIGINT",unique = true)
    private int mobile_no;
    
    @Column
    private String address;
    
    @ManyToOne
    @JoinColumn(name = "gender_lid")
    private Gender gender;

    @Column
    private String created_by;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_date ;

    @Column
    private String modified_by ;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date modified_date = new Date();

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @OneToMany(mappedBy = "user_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserModules> userModules = new ArrayList<>();

    @OneToMany(mappedBy = "user_lid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();


}
