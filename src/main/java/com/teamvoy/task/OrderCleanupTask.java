package com.teamvoy.task;

import com.teamvoy.model.Order;
import com.teamvoy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.teamvoy.constant.Constant.TIME_10_MINUTES;

@Component
public class OrderCleanupTask {
    @Autowired
    private OrderService orderService;

    @Scheduled(fixedRate = TIME_10_MINUTES)
    public void deleteUnpaidOrders() {
        List<Order> unpaidOrders = orderService.findByIsPaid(false);
        orderService.deleteAll(unpaidOrders);

    }
}
