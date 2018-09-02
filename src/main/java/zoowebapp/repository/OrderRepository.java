package zoowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zoowebapp.entity.Order;
import zoowebapp.entity.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :id  AND o.orderStatus = :status")
    Integer findNotConfirmedOrdersCountByUserId(@Param("id") Long id, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderStatus = :status")
    List<Order> findNotConfirmedOrdersByUserId(@Param("userId") Long userId, @Param("status") OrderStatus status);
}
