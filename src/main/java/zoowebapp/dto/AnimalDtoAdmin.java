package zoowebapp.dto;

import lombok.Data;
import zoowebapp.entity.Department;
import zoowebapp.entity.enums.AnimalGender;
import zoowebapp.entity.enums.AnimalStatus;

import java.util.Date;

@Data
public class AnimalDtoAdmin {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private String wikiUrl;

    private Date createdAt;

    private String department;

    private AnimalStatus status;

    private AnimalGender gender;

    private String illnessesHistory;

    private String globalNumber;
}
