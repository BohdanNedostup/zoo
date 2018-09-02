package zoowebapp.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="upload_file")
@NoArgsConstructor
@Getter
@Setter
public class UploadFile extends BaseEntity{

    private String name;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] fileData;
}
