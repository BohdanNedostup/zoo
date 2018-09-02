package zoowebapp.mapper;

import org.modelmapper.ModelMapper;
import zoowebapp.dto.OrderDtoAdmin;
import zoowebapp.dto.OrderDtoUser;
import zoowebapp.entity.Order;

public interface OrderMapper {

    static OrderDtoAdmin convertOrderToOrderDtoAdmin(Order order){
        OrderDtoAdmin orderDtoAdmin = new ModelMapper().map(order, OrderDtoAdmin.class);
        orderDtoAdmin.setUserId(order.getUser().getId());
        orderDtoAdmin.setProductId(order.getProduct().getId());
        orderDtoAdmin.setProductSize(order.getProductSize().getName());
        return orderDtoAdmin;
    }

    static Order convertOrderDtoAdminToOrder(OrderDtoAdmin orderDtoAdmin){
        return new ModelMapper().map(orderDtoAdmin, Order.class);
    }

    static OrderDtoUser convertOrderToOrderDtoUser(Order order){
        OrderDtoUser orderDtoUser = new ModelMapper().map(order, OrderDtoUser.class);
        orderDtoUser.setProduct(order.getProduct().getName());
        orderDtoUser.setProductSize(order.getProductSize().getName());
        return orderDtoUser;
    }
}
