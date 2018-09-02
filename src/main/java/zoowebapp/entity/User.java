package zoowebapp.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import zoowebapp.entity.enums.Country;
import zoowebapp.entity.enums.UserGender;
import zoowebapp.entity.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user", indexes = @Index(columnList = "first_name, last_name, email"))
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@ToString(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "profile_image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    @Column(unique = true)
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    private String token;

    @Column(name = "global_number", nullable = false, unique = true)
    private String globalNumber;
}