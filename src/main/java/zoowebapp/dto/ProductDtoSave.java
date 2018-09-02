package zoowebapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDtoSave {

    private String name;

    private BigDecimal price;

    private String category;

    private List<String> sizes;

    private String description;
}
