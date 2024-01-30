package ru.demidov.orderservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demidov.orderservice.dto.OrderFormDto;
import ru.demidov.orderservice.dto.OrderProductDto;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.entity.OrderStatus;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.repository.impl.OrderRepositoryImpl;
import ru.demidov.orderservice.service.OrderService;
import ru.demidov.orderservice.util.impl.OrderValidatorImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryImpl orderRepository;
    private final ProductServiceImpl productService;
    private final OrderProductServiceImpl orderProductService;
    private final OrderValidatorImpl orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl orderRepository, ProductServiceImpl productService, OrderProductServiceImpl orderProductService, OrderValidatorImpl orderValidator) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderProductService = orderProductService;
        this.orderValidator = orderValidator;
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
    public ResponseEntity<Order> create(OrderFormDto form) {
        log.info("Create orders to the database");
        List<OrderProductDto> formDtos = form.getProductOrders();
        orderValidator.validateProductsExistence(formDtos);

        Order order = new Order();
        order.setDateCreated(LocalDate.now());
        order.setStatus(OrderStatus.PAID.name());
        log.debug("create order {}", order);
        order = orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {

            log.info("Adding items to a new order");
            orderProducts.add(orderProductService.create(new OrderProduct(
                    order,
                    productService.getProduct(dto.getProduct().getId()),
                    dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Order> update(Long id, OrderFormDto form) {
        log.info("Update orders in the database by id");
        List<OrderProductDto> formsDto = form.getProductOrders();

        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(form.getQuantity())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(formsDto)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);


        log.debug("update order by id {}, list update product {}", id, formsDto);
        Order order = orderRepository.findById(id).orElse(null);

        if (isNull(order)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            order.setDateCreated(order.getDateCreated());

            List<OrderProduct> newProductsOrder = new ArrayList<>();

            for (OrderProductDto dto : formsDto) {

                newProductsOrder.add(orderProductService.update(
                        order,
                        productService.getProduct(dto.getProduct().getId()),
                        dto.getQuantity()));
            }

            order.setOrderProducts(newProductsOrder);

            log.debug("update order {}", order);
            orderRepository.update(order);

            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        log.info("Deleting orders from the database by id");
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        log.debug("deleteOrder {}", id);
        Order order = orderRepository.findById(id).orElse(null);

        if (isNull(order)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            log.debug("delete order {}", order);
            orderRepository.delete(order);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}
