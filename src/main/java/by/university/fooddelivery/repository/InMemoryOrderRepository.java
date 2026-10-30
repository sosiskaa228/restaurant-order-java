package by.university.fooddelivery.repository;

import by.university.fooddelivery.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Заказ не должен быть null.");
        }

        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}
