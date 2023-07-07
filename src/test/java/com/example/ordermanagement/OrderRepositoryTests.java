package com.example.ordermanagement;

import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        // Create a new Order object
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 Easy Street", 130.0);
        // Save the Order using the repository
        Order savedOrder = orderRepository.save(order);
        // Assert that the saved Order has an ID and the orderDate is set correctly
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderDate()); // Verify that the orderDate is set
        assertEquals(LocalDate.now(), savedOrder.getOrderDate()); // Verify that the orderDate is the current date
    }

    @Test
    public void testFindOrderById() {
        // Create and persist an Order using the entity manager
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 Easy Street", 130.0);
        entityManager.persist(order);
        // Retrieve the Order by ID using the repository
        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);
        // Assert that the retrieved Order is not null and has the expected customerName
        assertNotNull(foundOrder);
        assertEquals(order.getCustomerName(), foundOrder.getCustomerName());
    }

    @Test
    public void testUpdateOrder() {
        // Create and persist an Order using the entity manager
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 Easy Street", 130.0);
        entityManager.persist(order);
        // Update the customerName of the Order
        order.setCustomerName("Kelan Blash");
        // Save the updated Order using the repository
        Order updatedOrder = orderRepository.save(order);
        // Assert that the customerName of the updated Order is as expected
        assertEquals("Kelan Blash", updatedOrder.getCustomerName());
    }

    @Test
    public void testDeleteOrder() {
        // Create and persist an Order using the entity manager
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 Easy Street", 130.0);
        entityManager.persist(order);
        // Delete the Order by ID using the repository
        orderRepository.deleteById(order.getId());
        // Assert that the Order no longer exists in the repository
        assertFalse(orderRepository.existsById(order.getId()));
    }
}
