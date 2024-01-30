package ru.demidov.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.demidov.orderservice.dto.OrderFormDto;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.Product;
import ru.demidov.orderservice.service.impl.OrderServiceImpl;

import java.util.List;

@Slf4j
@Tag(name = "Order Controller")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
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
    public ResponseEntity<Order> createOrder(@RequestBody OrderFormDto form) {
        log.info("Adding orders to the database");
        return orderService.create(form);
    }

    @Operation(
            summary = "Editing orders in the database"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody OrderFormDto form) {
        log.info("Editing orders in the database by id");
        return orderService.update(id, form);
    }

    @Operation(
            summary = "Deleting orders from the database"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        log.info("Deleting orders from the database by id");
        return orderService.delete(id);
    }
}
