package zoowebapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoowebapp.dto.OrderDtoUser;
import zoowebapp.dto.filter.OrderUserFilter;
import zoowebapp.entity.Order;
import zoowebapp.entity.User;
import zoowebapp.entity.enums.OrderStatus;
import zoowebapp.mapper.OrderMapper;
import zoowebapp.service.OrderService;
import zoowebapp.service.UserService;
import zoowebapp.service.utils.DateUtils;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
public class OrderShopRestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Page<OrderDtoUser> getAllUserOrders(Principal principal,
                                               @PageableDefault Pageable pageable,
                                               @RequestParam BigDecimal summaryPriceFrom,
                                               @RequestParam BigDecimal summaryPriceTo,
                                               @RequestParam String createdAtFrom,
                                               @RequestParam String createdAtTo,
                                               @RequestParam List<String> statuses,
                                               @RequestParam String sortBy,
                                               @RequestParam String ascOrDesc){
        OrderUserFilter orderUserFilter = new OrderUserFilter(convertStringListToOrderStatusList(statuses),
                summaryPriceFrom, validateSummaryPriceTo(summaryPriceTo),
                DateUtils.convertStringToDate(createdAtFrom), DateUtils.convertStringToDate(createdAtTo), sortBy, ascOrDesc);
        User user = userService.findByEmail(principal.getName());
        Page<Order> ordersPage = orderService.findOrdersByUserIdByPageByFilter(user.getId(), orderUserFilter, pageable);
        List<OrderDtoUser> userOrders = new ArrayList<>();
        ordersPage.forEach(order -> userOrders.add(OrderMapper.convertOrderToOrderDtoUser(order)));
        return new PageImpl<>(userOrders, pageable, ordersPage.getTotalElements());
    }

    @GetMapping("statuses")
    public List<String> getAllOrderStatuses(){
        List<String> statuses = new ArrayList<>();
        Arrays.asList(OrderStatus.values()).forEach(status -> statuses.add(status.getStatus()));
        return statuses;
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> confirmOrders(Principal principal){

        User user = userService.findByEmail(principal.getName());
        List<Order> userNotConfirmedOrders = orderService.findNotConfirmedOrdersByUserId(user.getId());
        System.out.println("INSIDE");
        System.out.println(userNotConfirmedOrders);
        userNotConfirmedOrders.forEach(order -> order.setOrderStatus(OrderStatus.PROCESSING));

        userNotConfirmedOrders.forEach(order -> orderService.update(order));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
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

    private BigDecimal validateSummaryPriceTo(BigDecimal summaryPriceTo){
        if (summaryPriceTo.equals(new BigDecimal(0))){
            summaryPriceTo = new BigDecimal(Integer.MAX_VALUE);
        }
        return summaryPriceTo;
    }
}
