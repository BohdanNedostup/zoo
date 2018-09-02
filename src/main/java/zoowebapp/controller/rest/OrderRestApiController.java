package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoowebapp.dto.OrderDtoAdmin;
import zoowebapp.dto.filter.OrderAdminFilter;
import zoowebapp.entity.Order;
import zoowebapp.entity.enums.OrderStatus;
import zoowebapp.mapper.OrderMapper;
import zoowebapp.service.OrderService;
import zoowebapp.service.ProductService;
import zoowebapp.service.ProductSizeService;
import zoowebapp.service.UserService;
import zoowebapp.service.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/manage/orders")
public class OrderRestApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public Page<OrderDtoAdmin> getAllOrders(@PageableDefault Pageable pageable,
                                            @RequestParam Long userIdFrom,
                                            @RequestParam Long userIdTo,
                                            @RequestParam Long productIdFrom,
                                            @RequestParam Long productIdTo,
                                            @RequestParam Integer productQtyFrom,
                                            @RequestParam Integer productQtyTo,
                                            @RequestParam BigDecimal summaryPriceFrom,
                                            @RequestParam BigDecimal summaryPriceTo,
                                            @RequestParam String createdAtFrom,
                                            @RequestParam String createdAtTo,
                                            @RequestParam List<String> statuses,
                                            @RequestParam List<String> sizes,
                                            @RequestParam String sortBy,
                                            @RequestParam String ascOrDesc
                                            ){

        OrderAdminFilter orderAdminFilter = new OrderAdminFilter(userIdFrom, validateIdTo(userIdTo), productIdFrom, validateIdTo(productIdTo),
                productQtyFrom, validateProductQtyTo(productQtyTo), summaryPriceFrom, validateSummaryPriceTo(summaryPriceTo),
                DateUtils.convertStringToDate(createdAtFrom), DateUtils.convertStringToDate(createdAtTo),
                validateEmptySizeNamesList(sizes), convertStringListToOrderStatusList(statuses), sortBy, ascOrDesc);
        Page<Order> ordersPage = orderService.findOrdersByPageByFilter(orderAdminFilter, pageable);
        List<OrderDtoAdmin> orderDtoAdminList = new ArrayList<>();
        ordersPage.forEach(order -> orderDtoAdminList.add(OrderMapper.convertOrderToOrderDtoAdmin(order)));
        return new PageImpl<>(orderDtoAdminList, pageable, ordersPage.getTotalElements());
    }

    @PutMapping("")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderDtoAdmin orderDtoAdmin){
        Order order = orderService.findById(orderDtoAdmin.getId());
        if (order != null){
            order = OrderMapper.convertOrderDtoAdminToOrder(orderDtoAdmin);
            order.setUser(userService.findById(orderDtoAdmin.getUserId()));
            order.setProduct(productService.findById(orderDtoAdmin.getProductId()));
            order.setProductSize(productSizeService.findByName(orderDtoAdmin.getProductSize()));
            orderService.update(order);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    private Long validateIdTo(Long idTo){
        if (idTo == 0) {
            idTo = Long.MAX_VALUE;
        }
        return idTo;
    }

    private Integer validateProductQtyTo(Integer productQtyTo){
        if (productQtyTo == 0){
            productQtyTo = Integer.MAX_VALUE;
        }
        return productQtyTo;
    }

    private List<OrderStatus> convertStringListToOrderStatusList(List<String> statuses){
        if (statuses.size() == 0){
            return Arrays.asList(OrderStatus.values());
        } else {
            OrderStatus orderStatuses[] = OrderStatus.values();
            List<OrderStatus> resultStatusList = new ArrayList<>();
            for (int i = 0; i < orderStatuses.length; i++){
                for (int j = 0; j < statuses.size(); j++){
                    if (statuses.get(j).equals(orderStatuses[i].getStatus())){
                        resultStatusList.add(orderStatuses[i]);
                    }
                }
            }
            return resultStatusList;
        }
    }

    private List<String> validateEmptySizeNamesList(List<String> sizes){
        if (sizes.size() == 0){
            productSizeService.findAll().forEach(size -> sizes.add(size.getName()));
        }
        return sizes;
    }

    private BigDecimal validateSummaryPriceTo(BigDecimal summaryPriceTo){
        if (summaryPriceTo.equals(new BigDecimal(0))){
            summaryPriceTo = new BigDecimal(Integer.MAX_VALUE);
        }
        return summaryPriceTo;
    }
}
