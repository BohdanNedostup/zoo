package zoowebapp.dto;

import lombok.Data;
import zoowebapp.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDtoUser {

    private Long id;

    private String product;

    private OrderStatus orderStatus;

    private Integer productQty;

    private BigDecimal summaryPrice;

    private String productSize;

    private Date createdAt;
}
