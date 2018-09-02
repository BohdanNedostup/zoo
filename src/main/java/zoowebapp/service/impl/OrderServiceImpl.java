package zoowebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zoowebapp.dto.filter.OrderAdminFilter;
import zoowebapp.dto.filter.OrderUserFilter;
import zoowebapp.entity.Order;
import zoowebapp.entity.Product;
import zoowebapp.entity.ProductSize;
import zoowebapp.entity.User;
import zoowebapp.entity.enums.OrderStatus;
import zoowebapp.repository.OrderRepository;
import zoowebapp.service.OrderService;
import zoowebapp.service.ProductSizeService;
import zoowebapp.service.utils.DateUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductSizeService productSizeService;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Integer findNotConfirmedOrdersCountByUserId(Long id) {
        return orderRepository.findNotConfirmedOrdersCountByUserId(id, OrderStatus.NOT_CONFIRMED);
    }

    @Override
    public Page<Order> findOrdersByPageByFilter(OrderAdminFilter orderAdminFilter, Pageable pageable) {
        return orderRepository.findAll(getSpecification(orderAdminFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.getOne(id);
    }

    private Specification getSpecification(OrderAdminFilter orderAdminFilter){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Join<Order, User> orderUserJoin = root.join("user");
                Predicate predicateUserId = criteriaBuilder.between(orderUserJoin.get("id"), orderAdminFilter.getUserIdFrom(), orderAdminFilter.getUserIdTo());

                Join<Order, Product> orderProductJoin = root.join("product");
                Predicate predicateProductId = criteriaBuilder.between(orderProductJoin.get("id"), orderAdminFilter.getProductIdFrom(), orderAdminFilter.getProductIdTo());

                Predicate predicateProductQty = criteriaBuilder.between(root.get("productQty"), orderAdminFilter.getProductQtyFrom(), orderAdminFilter.getProductQtyTo());

                Predicate predicateSummaryPrice = criteriaBuilder.between(root.get("summaryPrice"), orderAdminFilter.getSummaryPriceFrom(), orderAdminFilter.getSummaryPriceTo());

                List<Long> sizesId = new ArrayList<>();
                productSizeService.findByNameList(orderAdminFilter.getSizes()).forEach(productSize -> sizesId.add(productSize.getId()));
                Join<Order, ProductSize> orderProductSizeJoin = root.join("productSize");
                Predicate predicateSize = orderProductSizeJoin.get("id").in(sizesId);

                Predicate predicateStatus = root.get("orderStatus").in(orderAdminFilter.getStatuses());

                Calendar calendarMin = Calendar.getInstance();
                Calendar calendarMax = Calendar.getInstance();
                calendarMin.set(1900, 0, 1);
                calendarMax.set(3000, 0, 1);

                orderAdminFilter.setCreatedAtTo(DateUtils.plusOneDay(orderAdminFilter.getCreatedAtTo()));

                Predicate predicateCreatedAt = orderAdminFilter.getCreatedAtFrom() == null ?
                        orderAdminFilter.getCreatedAtTo() == null ?
                                criteriaBuilder.between(root.get("createdAt"), calendarMin.getTime(), calendarMax.getTime()) :
                                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), orderAdminFilter.getCreatedAtTo()) :
                        orderAdminFilter.getCreatedAtTo() == null ?
                                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), orderAdminFilter.getCreatedAtFrom()) :
                                criteriaBuilder.between(root.get("createdAt"), orderAdminFilter.getCreatedAtFrom(), orderAdminFilter.getCreatedAtTo());

                switch(orderAdminFilter.getSortBy()) {
                    case "id": {
                        criteriaQuery.orderBy(orderAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "userId": {
                        criteriaQuery.orderBy(orderAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(orderUserJoin.get("id")) : criteriaBuilder.desc(orderUserJoin.get("id")));
                        break;
                    }
                    case "productId": {
                        criteriaQuery.orderBy(orderAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(orderProductJoin.get("id")) : criteriaBuilder.desc(orderProductJoin.get("id")));
                        break;
                    }
                    case "productQty": {
                        criteriaQuery.orderBy(orderAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("productQty")) : criteriaBuilder.desc(root.get("productQty")));
                        break;
                    }
                    case "summaryPrice": {
                        criteriaQuery.orderBy(orderAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("summaryPrice")) : criteriaBuilder.desc(root.get("summaryPrice")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(orderAdminFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }

                return criteriaBuilder.and(predicateUserId, predicateProductId, predicateProductQty, predicateSummaryPrice, predicateCreatedAt, predicateSize, predicateStatus);
            }
        };
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Page<Order> findOrdersByUserIdByPageByFilter(Long userId, OrderUserFilter orderUserFilter, Pageable pageable) {
        return orderRepository.findAll(getSpecification(userId, orderUserFilter), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    private Specification getSpecification(Long userId, OrderUserFilter orderUserFilter){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Order, User> orderUserJoin = root.join("user");
                Predicate predicateUser = criteriaBuilder.equal(orderUserJoin.get("id"), userId);

                Predicate predicateSummaryPrice = criteriaBuilder.between(root.get("summaryPrice"), orderUserFilter.getSummaryPriceFrom(), orderUserFilter.getSummaryPriceTo());

                Predicate predicateStatus = root.get("orderStatus").in(orderUserFilter.getStatuses());

                Calendar calendarMin = Calendar.getInstance();
                Calendar calendarMax = Calendar.getInstance();
                calendarMin.set(1900, 0, 1);
                calendarMax.set(3000, 0, 1);

                orderUserFilter.setCreatedAtTo(DateUtils.plusOneDay(orderUserFilter.getCreatedAtTo()));

                Predicate predicateCreatedAt = orderUserFilter.getCreatedAtFrom() == null ?
                        orderUserFilter.getCreatedAtTo() == null ?
                                criteriaBuilder.between(root.get("createdAt"), calendarMin.getTime(), calendarMax.getTime()) :
                                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), orderUserFilter.getCreatedAtTo()) :
                        orderUserFilter.getCreatedAtTo() == null ?
                                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), orderUserFilter.getCreatedAtFrom()) :
                                criteriaBuilder.between(root.get("createdAt"), orderUserFilter.getCreatedAtFrom(), orderUserFilter.getCreatedAtTo());

                switch(orderUserFilter.getSortBy()) {
                    case "id": {
                        criteriaQuery.orderBy(orderUserFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    }
                    case "productQty": {
                        criteriaQuery.orderBy(orderUserFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("productQty")) : criteriaBuilder.desc(root.get("productQty")));
                        break;
                    }
                    case "summaryPrice": {
                        criteriaQuery.orderBy(orderUserFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("summaryPrice")) : criteriaBuilder.desc(root.get("summaryPrice")));
                        break;
                    }
                    case "createdAt": {
                        criteriaQuery.orderBy(orderUserFilter.getAscOrDesc().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    }
                }
                return criteriaBuilder.and(predicateUser, predicateStatus, predicateSummaryPrice, predicateCreatedAt);
            }
        };
    }

    @Override
    public List<Order> findNotConfirmedOrdersByUserId(Long userId) {
        return orderRepository.findNotConfirmedOrdersByUserId(userId, OrderStatus.NOT_CONFIRMED);
    }
}
