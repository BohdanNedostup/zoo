package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnimalGalleryFilter {

    private String name;
    private List<String> departments;

    private String sortBy;
    private String ascOrDesc;
}
