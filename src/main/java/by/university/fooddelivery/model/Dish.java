package by.university.fooddelivery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish {

    private final long id;
    private final String name;
    private final BigDecimal price;

    public Dish(long id, String name, BigDecimal price) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID блюда должен быть положительным.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название блюда не может быть пустым.");
        }

        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("Цена блюда должна быть больше нуля.");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Dish dish)) {
            return false;
        }

        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " - " + price + " BYN";
    }
}
