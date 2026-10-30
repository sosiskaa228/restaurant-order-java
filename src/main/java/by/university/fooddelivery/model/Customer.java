package by.university.fooddelivery.model;

import java.util.Objects;

public class Customer {

    private final long id;
    private final String name;
    private final String phone;

    public Customer(long id, String name, String phone) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID клиента должен быть положительным.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя клиента не может быть пустым.");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Телефон клиента не может быть пустым.");
        }

        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Customer customer)) {
            return false;
        }

        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
