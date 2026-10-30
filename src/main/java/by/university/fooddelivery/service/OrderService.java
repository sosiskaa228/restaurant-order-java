package by.university.fooddelivery.service;

import by.university.fooddelivery.model.Order;
import by.university.fooddelivery.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderService {

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
            throw new IllegalArgumentException("Заказ не должен быть null.");
        }

        BigDecimal totalAmount = order.calculateTotalAmount();

        if (totalAmount.compareTo(MIN_ORDER_AMOUNT) < 0) {
            throw new IllegalStateException(
                    "Невозможно оформить заказ. Минимальная сумма заказа: "
                            + MIN_ORDER_AMOUNT
                            + " BYN. Текущая сумма: "
                            + totalAmount
                            + " BYN."
            );
        }

        order.markCreated();

        return orderRepository.save(order);
    }
}
