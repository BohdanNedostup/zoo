package zoowebapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department", indexes = @Index(columnList = "name"))
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Department extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Animal> animals = new ArrayList<>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}