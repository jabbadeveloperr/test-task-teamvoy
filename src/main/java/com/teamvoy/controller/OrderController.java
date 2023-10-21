package com.teamvoy.controller;

import com.teamvoy.dto.OrderDTO;
import com.teamvoy.model.Order;
import com.teamvoy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PutMapping("/{orderId}/markAsPaid")
    public ResponseEntity<String> markOrderAsPaid(@PathVariable Long orderId) {
        orderService.markOrderAsPaid(orderId);
        return ResponseEntity.ok("Order with ID:" + orderId + " marked as paid successfully");
    }

}
