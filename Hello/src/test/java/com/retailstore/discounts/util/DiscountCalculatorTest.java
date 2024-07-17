package com.retailstore.discounts.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retailstore.model.Product;
import retailstore.model.User;
import retailstore.model.UserType;
import retailstore.util.DiscountCalculator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DiscountCalculatorTest {

    @Mock
    private User user;

    @InjectMocks
    private DiscountCalculator discountCalculator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculatePercentageDiscount_Employee() {
        // Setup
        when(user.getUserType()).thenReturn(UserType.EMPLOYEE);
        when(user.getCustomerSince()).thenReturn(LocalDate.now().minusYears(3));

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(1000.0);

        List<Product> products = Arrays.asList(product1);

        // Test
        double discount = discountCalculator.calculatePercentageDiscount(user, products);
        assertEquals(300.0, discount); // 30% of 1000
    }

    @Test
    public void testCalculatePercentageDiscount_Affiliate() {
        // Setup
        when(user.getUserType()).thenReturn(UserType.AFFILIATE);
        when(user.getCustomerSince()).thenReturn(LocalDate.now().minusYears(1));

        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("Phone");
        product1.setPrice(500.0);

        List<Product> products = Arrays.asList(product1);

        // Test
        double discount = discountCalculator.calculatePercentageDiscount(user, products);
        assertEquals(50.0, discount); // 10% of 500
    }

    @Test
    public void testCalculatePercentageDiscount_CustomerOverTwoYears() {
        // Setup
        when(user.getUserType()).thenReturn(UserType.CUSTOMER);
        when(user.getCustomerSince()).thenReturn(LocalDate.now().minusYears(3));

        Product product1 = new Product();
        product1.setId(3L);
        product1.setName("Tablet");
        product1.setPrice(300.0);

        List<Product> products = Arrays.asList(product1);

        // Test
        double discount = discountCalculator.calculatePercentageDiscount(user, products);
        assertEquals(15.0, discount); // 5% of 300
    }

    @Test
    public void testCalculatePercentageDiscount_GroceriesOnly() {
        // Setup
        when(user.getUserType()).thenReturn(UserType.EMPLOYEE);
        when(user.getCustomerSince()).thenReturn(LocalDate.now().minusYears(3));

        Product product1 = new Product();
        product1.setId(4L);
        product1.setName("Apple");
        product1.setPrice(100.0);

        List<Product> products = Arrays.asList(product1);

        // Test
        double discount = discountCalculator.calculatePercentageDiscount(user, products);
        assertEquals(30.0, discount); // No percentage discount on groceries
    }

    @Test
    public void testCalculateFlatDiscount() {
        // Test
        double totalAmount = 990.0;
        double discount = discountCalculator.calculateFlatDiscount(totalAmount);
        assertEquals(45.0, discount); // For $990, $45 discount

        totalAmount = 1000.0;
        discount = discountCalculator.calculateFlatDiscount(totalAmount);
        assertEquals(50.0, discount); // For $1000, $50 discount
    }
}
