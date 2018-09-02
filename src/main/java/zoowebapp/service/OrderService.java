package zoowebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zoowebapp.dto.filter.OrderAdminFilter;
import zoowebapp.dto.filter.OrderUserFilter;
import zoowebapp.entity.Order;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    void deleteById(Long id);

    List<Order> findAll();

    Order findById(Long id);

    List<Order> findByUserId(Long userId);

    Integer findNotConfirmedOrdersCountByUserId(Long id);

    Page<Order> findOrdersByPageByFilter(OrderAdminFilter orderAdminFilter, Pageable pageable);

    Page<Order> findOrdersByUserIdByPageByFilter(Long userId, OrderUserFilter orderUserFilter, Pageable pageable);

    List<Order> findNotConfirmedOrdersByUserId(Long userId);
}
