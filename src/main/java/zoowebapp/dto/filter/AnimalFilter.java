package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import zoowebapp.entity.enums.AnimalGender;
import zoowebapp.entity.enums.AnimalStatus;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class AnimalFilter {

    private String name;
    private Date createdAtFrom;
    private Date createdAtTo;
    private List<AnimalStatus> statuses;
    private List<AnimalGender> genders;
    private List<String> departments;

    private String sortBy;
    private String ascOrDesc;
}
