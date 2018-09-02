package zoowebapp.entity;

import lombok.*;
import zoowebapp.entity.enums.AnimalGender;
import zoowebapp.entity.enums.AnimalStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "animal", indexes = @Index(columnList = "name"))
@NoArgsConstructor
@Getter @Setter
@Builder
@AllArgsConstructor
public class Animal extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Lob
    private String description;

    @Column(name = "illnesses_history")
    @Lob
    private String illnessesHistory;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "wiki_url", nullable = false)
    private String wikiUrl;

    @Column(nullable = false)
    private AnimalStatus status;

    @Column(nullable = false)
    private AnimalGender gender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(name = "global_number", nullable = false, unique = true)
    private String globalNumber;
}