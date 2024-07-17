package com.retailstore.discounts.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retailstore.model.*;
import retailstore.service.DiscountService;
import retailstore.util.DiscountCalculator;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DiscountServiceTest {

    @Mock
    private DiscountCalculator discountCalculator;

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateNetPayableAmount_Employee() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setUserType(UserType.EMPLOYEE);
        user.setCustomerSince(LocalDate.now().minusYears(3));

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setCategory(ProductCategory.OTHER);
        product1.setPrice(1000.0);

        Bill bill = new Bill();
        bill.setId(1L);
        bill.setUser(user);
        bill.setProducts(Arrays.asList(product1));

        double totalAmount = 1000.0;
        double percentageDiscount = 300.0; // 30% of 1000
        double flatDiscount = 45.0; // For $1000, $45 discount

        when(discountCalculator.calculatePercentageDiscount(user, bill.getProducts())).thenReturn(percentageDiscount);
        when(discountCalculator.calculateFlatDiscount(totalAmount)).thenReturn(flatDiscount);

        double netPayableAmount = discountService.calculateNetPayableAmount(bill);
        assertEquals(650.0, netPayableAmount);
    }

    @Test
    public void testCalculateNetPayableAmount_Affiliate() {
        User user = new User();
        user.setId(2L);
        user.setName("Jane");
        user.setUserType(UserType.AFFILIATE);
        user.setCustomerSince(LocalDate.now().minusYears(1));

        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("Phone");
        product1.setCategory(ProductCategory.OTHER);
        product1.setPrice(500.0);

        Bill bill = new Bill();
        bill.setId(2L);
        bill.setUser(user);
        bill.setProducts(Arrays.asList(product1));

        double totalAmount = 500.0;
        double percentageDiscount = 50.0; // 10% of 500
        double flatDiscount = 20.0; // For $500, $20 discount

        when(discountCalculator.calculatePercentageDiscount(user, bill.getProducts())).thenReturn(percentageDiscount);
        when(discountCalculator.calculateFlatDiscount(totalAmount)).thenReturn(flatDiscount);

        double netPayableAmount = discountService.calculateNetPayableAmount(bill);
        assertEquals(425.0, netPayableAmount);
    }

    @Test
    public void testCalculateNetPayableAmount_CustomerOverTwoYears() {
        User user = new User();
        user.setId(3L);
        user.setName("Alice");
        user.setUserType(UserType.CUSTOMER);
        user.setCustomerSince(LocalDate.now().minusYears(3));

        Product product1 = new Product();
        product1.setId(3L);
        product1.setName("Tablet");
        product1.setCategory(ProductCategory.OTHER);
        product1.setPrice(300.0);

        Bill bill = new Bill();
        bill.setId(3L);
        bill.setUser(user);
        bill.setProducts(Arrays.asList(product1));

        double totalAmount = 300.0;
        double percentageDiscount = 15.0; // 5% of 300
        double flatDiscount = 15.0; // For $300, $15 discount

        when(discountCalculator.calculatePercentageDiscount(user, bill.getProducts())).thenReturn(percentageDiscount);
        when(discountCalculator.calculateFlatDiscount(totalAmount)).thenReturn(flatDiscount);

        double netPayableAmount = discountService.calculateNetPayableAmount(bill);
        assertEquals(270.0, netPayableAmount); // 300 - 15 - 15 = 270
    }

    @Test
    public void testCalculateNetPayableAmount_GroceriesOnly() {
        User user = new User();
        user.setId(4L);
        user.setName("Bob");
        user.setUserType(UserType.EMPLOYEE);
        user.setCustomerSince(LocalDate.now().minusYears(3));

        Product product1 = new Product();
        product1.setId(4L);
        product1.setName("Apple");
        product1.setCategory(ProductCategory.GROCERIES);
        product1.setPrice(100.0);

        Bill bill = new Bill();
        bill.setId(4L);
        bill.setUser(user);
        bill.setProducts(Arrays.asList(product1));

        double totalAmount = 100.0;
        double percentageDiscount = 0.0; // No percentage discount on groceries
        double flatDiscount = 5.0; // For $100, $5 discount

        when(discountCalculator.calculatePercentageDiscount(user, bill.getProducts())).thenReturn(percentageDiscount);
        when(discountCalculator.calculateFlatDiscount(totalAmount)).thenReturn(flatDiscount);

        double netPayableAmount = discountService.calculateNetPayableAmount(bill);
        assertEquals(95.0, netPayableAmount); // 100 - 0 - 5 = 95
    }
}
