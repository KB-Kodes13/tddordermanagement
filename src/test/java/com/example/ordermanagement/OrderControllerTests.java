package com.example.ordermanagement;

import com.example.ordermanagement.controller.OrderController;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createOrder_ValidInput_ReturnsCreatedStatus() throws Exception {
        // Arrange
        // Create a valid Order object
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 EZ Street", 200.0);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act & Assert
        // Send a POST request to create an order and expect a Created status
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"Kelan Blash\", \"shippingAddress\": \"123 EZ Street\", \"total\": 200.0 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // Other test methods follow the same structure with different scenarios and assertions
    @Test
    public void getAllOrders_ReturnsListOfOrders() throws Exception {
        // Arrange
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Kelan Blash", LocalDate.now(), "123 EZ Street", 200.0));
        orders.add(new Order("Taty Phelps", LocalDate.now(), "305 Ocean Drive", 300.0));
        when(orderRepository.findAll()).thenReturn(orders);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void getOrderById_ExistingOrderId_ReturnsOrder() throws Exception {
        // Arrange
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 EZ Street", 200.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Kelan Blash"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("123 EZ Street"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(200.0));
    }

    @Test
    public void getOrderById_NonexistentOrderId_ReturnsNotFoundStatus() throws Exception {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateOrder_ValidInput_ReturnsUpdatedOrder() throws Exception {
        // Arrange
        Order order = new Order("Kelan Blash", LocalDate.now(), "123 Main Street", 200.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"Updated Name\", \"shippingAddress\": \"305 Ocean Drive\", \"total\": 300.0 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Updated Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("305 Ocean Drive"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(300.0));
    }

    @Test
    public void createOrder_EmptyFields_ReturnsInternalServerError() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"\", \"shippingAddress\": \"\", \"total\": null }"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void updateOrder_NonexistentOrderId_ReturnsNotFoundStatus() throws Exception {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"Updated Name\", \"shippingAddress\": \"305 Ocean Drive\", \"total\": 300.0 }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteOrder_ExistingOrderId_ReturnsOkStatus() throws Exception {
        // Arrange
        when(orderRepository.existsById(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
    }

    @Test
    public void deleteOrder_NonexistentOrderId_ReturnsNotFoundStatus() throws Exception {
        // Arrange
        when(orderRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
