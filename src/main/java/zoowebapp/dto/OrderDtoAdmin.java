package zoowebapp.dto;

import lombok.Data;
import zoowebapp.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDtoAdmin {

    private Long id;

    private Long userId;

    private Long productId;

    private OrderStatus orderStatus;

    private Integer productQty;

    private BigDecimal summaryPrice;

    private String productSize;

    private Date createdAt;
}
