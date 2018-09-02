package zoowebapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
public class News extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String text;

    private String imageUrl;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}