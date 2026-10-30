package by.university.fooddelivery.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {

    private final long id;
    private final Customer customer;
    private final List<Dish> dishes;
    private OrderStatus status;

    public Order(long id, Customer customer, List<Dish> dishes) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID заказа должен быть положительным.");
        }

        if (customer == null) {
            throw new IllegalArgumentException("Клиент не должен быть null.");
        }

        if (dishes == null || dishes.isEmpty()) {
            throw new IllegalArgumentException("Заказ должен содержать хотя бы одно блюдо.");
        }

        this.id = id;
        this.customer = customer;
        this.dishes = new ArrayList<>(dishes);
        this.status = OrderStatus.NEW;
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Dish> getDishes() {
        return Collections.unmodifiableList(dishes);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal calculateTotalAmount() {
        return dishes.stream()
                .map(Dish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void markCreated() {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException(
                    "Оформить можно только новый заказ. Текущий статус: " + status
            );
        }

        status = OrderStatus.CREATED;
    }

    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Заказ уже отменён.");
        }

        status = OrderStatus.CANCELLED;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Order order)) {
            return false;
        }

        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer.getName() +
                ", dishes=" + dishes +
                ", total=" + calculateTotalAmount() + " BYN" +
                ", status=" + status +
                '}';
    }
}
