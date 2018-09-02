package zoowebapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductDtoShop {

    private Long id;

    private String name;

    private String category;

    private String imageUrl;

    private BigDecimal price;

    private List<String> sizes;

    private Date createdAt = new Date();

}
