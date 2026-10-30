package by.university.fooddelivery;

import by.university.fooddelivery.model.Customer;
import by.university.fooddelivery.model.Dish;
import by.university.fooddelivery.model.Order;
import by.university.fooddelivery.repository.InMemoryOrderRepository;
import by.university.fooddelivery.repository.OrderRepository;
import by.university.fooddelivery.service.OrderService;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        System.out.println("Режим сборки: " + loadDeliveryMode());

        OrderRepository orderRepository = new InMemoryOrderRepository();
        OrderService orderService = new OrderService(orderRepository);

        Customer customer = new Customer(
                1,
                "Иван Иванов",
                "+375291234567"
        );

        demonstrateOrderAboveMinimum(orderService, customer);
        demonstrateOrderEqualToMinimum(orderService, customer);
        demonstrateOrderBelowMinimum(orderService, orderRepository, customer);

        System.out.println("\nВсе оформленные заказы:");
        orderRepository.findAll().forEach(System.out::println);
    }

    private static void demonstrateOrderAboveMinimum(
            OrderService orderService,
            Customer customer
    ) {
        System.out.println("Сценарий 1: сумма больше минимума");

        try {
            Order order = new Order(
                    1,
                    customer,
                    List.of(
                            new Dish(1, "Пицца Пепперони", new BigDecimal("18.50")),
                            new Dish(2, "Кола", new BigDecimal("3.00"))
                    )
            );

            Order savedOrder = orderService.placeOrder(order);
            System.out.println("Успех: заказ оформлен.");
            System.out.println(savedOrder);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }

    private static void demonstrateOrderEqualToMinimum(
            OrderService orderService,
            Customer customer
    ) {
        System.out.println("\nСценарий 2: сумма равна минимуму");

        try {
            Order order = new Order(
                    2,
                    customer,
                    List.of(new Dish(3, "Маленькая пицца", new BigDecimal("20.00")))
            );

            Order savedOrder = orderService.placeOrder(order);
            System.out.println("Успех: заказ оформлен.");
            System.out.println(savedOrder);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }

    private static void demonstrateOrderBelowMinimum(
            OrderService orderService,
            OrderRepository orderRepository,
            Customer customer
    ) {
        System.out.println("\nСценарий 3: сумма меньше минимума");

        try {
            Order order = new Order(
                    3,
                    customer,
                    List.of(
                            new Dish(4, "Кофе", new BigDecimal("4.50")),
                            new Dish(5, "Десерт", new BigDecimal("8.00"))
                    )
            );

            orderService.placeOrder(order);
            System.out.println("Ошибка логики: заказ не должен был оформиться.");
        } catch (IllegalStateException exception) {
            System.out.println("Ожидаемый отказ: " + exception.getMessage());
            System.out.println(
                    "Заказ сохранён в хранилище: "
                            + orderRepository.findById(3).isPresent()
            );
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }

    private static String loadDeliveryMode() {
        try (InputStream input = Main.class.getResourceAsStream("/application.properties")) {
            if (input == null) {
                return "day";
            }

            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty("delivery.mode", "day");
        } catch (IOException exception) {
            return "day";
        }
    }
}
