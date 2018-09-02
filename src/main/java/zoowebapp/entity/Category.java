package zoowebapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category", indexes = @Index(columnList = "name"))
@NoArgsConstructor
@Getter @Setter
public class Category extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }
}
