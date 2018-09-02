package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import zoowebapp.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderUserFilter {

    private List<OrderStatus> statuses;
    private BigDecimal summaryPriceFrom;
    private BigDecimal summaryPriceTo;
    private Date createdAtFrom;
    private Date createdAtTo;

    private String sortBy;
    private String ascOrDesc;
}
