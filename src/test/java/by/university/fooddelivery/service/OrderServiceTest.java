package by.university.fooddelivery.service;

import by.university.fooddelivery.model.Customer;
import by.university.fooddelivery.model.Dish;
import by.university.fooddelivery.model.Order;
import by.university.fooddelivery.model.OrderStatus;
import by.university.fooddelivery.repository.InMemoryOrderRepository;
import by.university.fooddelivery.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;
    private Customer customer;

    @Before
    public void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderService = new OrderService(orderRepository);
        customer = new Customer(1, "Иван Иванов", "+375291234567");
    }

    @Test
    public void placeOrderSucceedsWhenAmountAboveMinimum() {
        Order order = new Order(
                1,
                customer,
                Arrays.asList(
                        new Dish(1, "Пицца Пепперони", new BigDecimal("18.50")),
                        new Dish(2, "Кола", new BigDecimal("3.00"))
                )
        );

        Order savedOrder = orderService.placeOrder(order);

        assertSame(order, savedOrder);
        assertEquals(OrderStatus.CREATED, savedOrder.getStatus());
        assertEquals(new BigDecimal("21.50"), savedOrder.calculateTotalAmount());
        assertTrue(orderRepository.findById(1).isPresent());
    }

    @Test
    public void placeOrderFailsWhenOrderIsNull() {
        try {
            orderService.placeOrder(null);
            fail("Заказ не может быть пустым.");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("null"));
            assertTrue(orderRepository.findAll().isEmpty());
        }
    }

    @Test
    public void placeOrderFailsWhenAmountBelowMinimum() {
        Order order = new Order(
                3,
                customer,
                Arrays.asList(
                        new Dish(4, "Кофе", new BigDecimal("4.50")),
                        new Dish(5, "Десерт", new BigDecimal("8.00"))
                )
        );

        try {
            orderService.placeOrder(order);
            fail("Заказ с суммой меньше минимальной не должен быть оформлен.");
        } catch (IllegalStateException exception) {
            assertTrue(exception.getMessage().contains("20.00"));
            assertEquals(OrderStatus.NEW, order.getStatus());
            assertTrue(orderRepository.findById(3).isEmpty());
        }
    }
}
