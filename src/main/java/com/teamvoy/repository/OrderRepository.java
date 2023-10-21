package com.teamvoy.repository;

import com.teamvoy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByIsPaid(boolean isPaid);
}