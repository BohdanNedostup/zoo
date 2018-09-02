package zoowebapp.dto;

import lombok.Data;

@Data
public class AnimalDtoSave {

    private String name;

    private String description;

    private String illnessesHistory;

    private String wikiUrl;

    private String status;

    private String gender;

    private String department;
}
