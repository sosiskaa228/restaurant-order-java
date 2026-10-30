package by.university.fooddelivery.repository;

import by.university.fooddelivery.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(long id);

    List<Order> findAll();
}
