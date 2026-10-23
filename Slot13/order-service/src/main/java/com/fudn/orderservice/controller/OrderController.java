package com.fudn.orderservice.controller;

import com.fudn.orderservice.dto.CreateOrderRequest;
import com.fudn.orderservice.entity.Order;
import com.fudn.orderservice.entity.SagaLog;
import com.fudn.orderservice.repository.OrderRepository;
import com.fudn.orderservice.repository.SagaLogRepository;
import com.fudn.orderservice.saga.OrderSagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderSagaOrchestrator orchestrator;
    private final OrderRepository orderRepository;
    private final SagaLogRepository sagaLogRepository;

    /**
     * POST /api/orders
     * Chạy Saga Orchestrator và trả về thông tin order kèm status & sagaStatus.
     */
    @PostMapping
    public Order placeOrder(@RequestBody CreateOrderRequest req) {
        return orchestrator.placeOrder(req);
    }

    /**
     * GET /api/orders
     * Lấy danh sách toàn bộ các order.
     */
    @GetMapping
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * GET /api/orders/{id}/saga-logs
     * Lấy danh sách các bước SagaLog của một order theo thứ tự ID / thời gian.
     */
    @GetMapping("/{id}/saga-logs")
    public List<SagaLog> sagaLogs(@PathVariable Long id) {
        return sagaLogRepository.findByOrderIdOrderByIdAsc(id);
    }
}
