package zoowebapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductDtoAdmin {

    private Long id;

    private String name;

    private String category;

    private String imageUrl;

    private String description;

    private BigDecimal price;

    private List<String> sizes;


    private String globalNumber;

    private Date createdAt;
}
