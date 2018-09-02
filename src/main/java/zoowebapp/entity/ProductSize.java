package zoowebapp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "product_size", indexes = @Index(columnList = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSize extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;
}
