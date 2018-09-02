package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import zoowebapp.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderAdminFilter {

    private Long userIdFrom;
    private Long userIdTo;
    private Long productIdFrom;
    private Long productIdTo;
    private Integer productQtyFrom;
    private Integer productQtyTo;
    private BigDecimal summaryPriceFrom;
    private BigDecimal summaryPriceTo;
    private Date createdAtFrom;
    private Date createdAtTo;
    private List<String> sizes;
    private List<OrderStatus> statuses;

    private String sortBy;
    private String ascOrDesc;
}
