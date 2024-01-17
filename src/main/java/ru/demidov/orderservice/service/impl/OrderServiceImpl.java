package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.dto.OrderProductDto;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.impl.OrderRepositoryImpl;
import ru.demidov.orderservice.service.OrderService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryImpl orderRepository;
    private final ProductServiceImpl productService;
    private final OrderProductServiceImpl orderProductService;

    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl orderRepository, ProductServiceImpl productService, OrderProductServiceImpl orderProductService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderProductService = orderProductService;
    }

    @Override
    public List<Order> getAllOrders() {
        log.debug("find all orders");
        return orderRepository.findAll();
    }

    @Override
    public List<Product> findProductsByIdOrder(Long id){
        log.debug("findProductsByIdOrder {}", id);
        return orderRepository.findProductsByIdOrder(id);
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());

        log.debug("save order {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, List<OrderProductDto> formDtos){
        Order order = orderRepository.findById(id).orElse(null);

        if (isNull(order)) {
            return null;
        }

        boolean needUpdate = false;

        if (nonNull(formDtos)) {
            order.setDateCreated(order.getDateCreated());

            List<OrderProduct> newProductsOrder = new ArrayList<>();

            for (OrderProductDto dto : formDtos) {

                newProductsOrder.add(orderProductService.update(
                        order,
                        productService.getProduct(dto.getProduct().getId()),
                        dto.getQuantity()));
            }

            order.setOrderProducts(newProductsOrder);

            needUpdate = true;
        }

        if (needUpdate) {
            log.debug("update order {}", order);
            orderRepository.update(order);
        }

        return order;
    }

    @Override
    public Order deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (isNull(order)) {
            return null;
        }

        log.debug("delete order {}", order);
        orderRepository.delete(order);
        return order;
    }
}
