package ru.demidov.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.demidov.orderservice.dto.OrderFormDto;
import ru.demidov.orderservice.dto.OrderProductDto;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.OrderProduct;
import ru.demidov.orderservice.entity.OrderStatus;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.service.impl.OrderProductServiceImpl;
import ru.demidov.orderservice.service.impl.OrderServiceImpl;
import ru.demidov.orderservice.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Slf4j
@Tag(name = "Order Controller")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;
    private final OrderProductServiceImpl orderProductService;

    @Autowired
    public OrderController(ProductServiceImpl productService,
                           OrderServiceImpl orderService,
                           OrderProductServiceImpl orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @Operation(
            summary = "Getting all orders from the database"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> list() {
        log.info("Getting all orders from the database");
        return this.orderService.getAllOrders();
    }

    @Operation(
            summary = "Getting a list of products from an order"
    )
    @GetMapping("/products/{id}")
    public List<Product> getListProducts(@PathVariable("id") Long orderId){
        log.info("Getting a list of products by order id");
        log.debug("getListProducts by order id {}", orderId);
        return orderService.findProductsByIdOrder(orderId);
    }

    @Operation(
            summary = "Adding orders to the database"
    )
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderFormDto form) {
        log.info("Adding orders to the database");
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);

        Order order = new Order();
        order.setStatus(OrderStatus.PAID.name());
        log.debug("create order {}", order);
        order = orderService.create(order);

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

    @Operation(
            summary = "Editing orders in the database"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody OrderFormDto form) {
        log.info("Editing orders in the database by id");
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(form.getQuantity())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (isNull(form.getProductOrders())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        log.debug("update order by id {}, list update product {}", id, form.getProductOrders());
        Order order = orderService.updateOrder(id, form.getProductOrders());
        if (isNull(order)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
    }

    @Operation(
            summary = "Deleting orders from the database"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        log.info("Deleting orders from the database by id");
        if (id <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        log.debug("deleteOrder {}", id);
        Order order = orderService.deleteOrder(id);
        if (isNull(order)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op
                        .getProduct()
                        .getId())))
                .toList();

        if (!CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("Product not found");
        }
    }
}
