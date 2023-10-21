package com.teamvoy.service;

import com.teamvoy.dto.OrderDTO;

import com.teamvoy.dto.OrderItemDTO;
import com.teamvoy.exeption.InsufficientQuantityException;
import com.teamvoy.exeption.OrderNotFoundException;
import com.teamvoy.exeption.ProductNotFoundException;
import com.teamvoy.model.Order;
import com.teamvoy.model.Product;
import com.teamvoy.repository.OrderRepository;
import com.teamvoy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void placeOrder(OrderDTO orderDTO) {
        Long clientId = orderDTO.getClientId();
        List<OrderItemDTO> orderItems = orderDTO.getOrderItems();

        for (OrderItemDTO itemDTO : orderItems) {
            Long productId = itemDTO.getProductId();
            int quantity = itemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (product.getQuantity() < quantity) {
                throw new InsufficientQuantityException("Insufficient quantity of product: " + product.getName());
            }

            Order order = new Order();
            order.setClientId(clientId);
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setIsPaid(false);

            orderRepository.save(order);

            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Transactional

    public void markOrderAsPaid(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setIsPaid(true);
            orderRepository.save(order);
        } else {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
    }

    public List<Order> findByIsPaid(boolean isPaid) {
        return orderRepository.findByIsPaid(isPaid);
    }

    public void deleteAll(List<Order> orders) {
        orderRepository.deleteAll(orders);
    }
}
