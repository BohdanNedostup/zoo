package zoowebapp.dto;

import lombok.Data;
import zoowebapp.entity.Department;

@Data
public class AnimalDtoGallery {

    private String name;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private String department;
}
