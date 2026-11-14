package by.university.fooddelivery.service;

import by.university.fooddelivery.model.Order;
import by.university.fooddelivery.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    public static final BigDecimal MIN_ORDER_AMOUNT = new BigDecimal("20.00");

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(
                orderRepository,
                "Репозиторий заказов не должен быть null."
        );
    }

    public Order placeOrder(Order order) {
        if (order == null) {
            LOG.error("Order operation failed: order is null");
            throw new IllegalArgumentException("Заказ не должен быть null.");
        }

        LOG.info("Order request received, id={}", order.getId());

        BigDecimal totalAmount = order.calculateTotalAmount();

        if (totalAmount.compareTo(MIN_ORDER_AMOUNT) < 0) {
            LOG.warn("Order total is below the minimum, id={}, total={}", order.getId(), totalAmount);
            throw new IllegalStateException(
                    "Невозможно оформить заказ. Минимальная сумма заказа: "
                            + MIN_ORDER_AMOUNT
                            + " BYN. Текущая сумма: "
                            + totalAmount
                            + " BYN."
            );
        }

        order.markCreated();
        Order savedOrder = orderRepository.save(order);
        LOG.info("Order placed successfully, id={}", savedOrder.getId());

        return savedOrder;
    }
}
