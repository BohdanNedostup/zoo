package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductShopFilter {

    private String name;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private List<String> categories;
    private List<String> sizes;

    private String sortBy;
    private String ascOrDesc;
}
