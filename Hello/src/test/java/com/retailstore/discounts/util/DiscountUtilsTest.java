package com.retailstore.discounts.util;

import org.junit.jupiter.api.Test;
import retailstore.model.Bill;
import retailstore.model.Product;
import retailstore.model.User;
import retailstore.model.UserType;
import retailstore.util.DiscountUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountUtilsTest {

    @Test
    void testCalculateNetPayableAmount_employeeDiscount() {
        // Create a bill with an employee user and non-grocery products
        User user = new User(UserType.EMPLOYEE, LocalDate.now().minusYears(1)); // Employee for less than 2 years
        List<Product> products = Arrays.asList(
                new Product("Product1", 100.0, false),
                new Product("Product2", 50.0, false)
        );
        Bill bill = new Bill(user,products);
        double netPayableAmount = DiscountUtils.calculateNetPayableAmount(bill);

        double expectedAmount = 100.0 + 45.0 - 45.0;
        assertEquals(expectedAmount, netPayableAmount, 0.01);
    }


    @Test
    void testCalculateNetPayableAmount_affiliateDiscount() {
        // Create a bill with an affiliate user and non-grocery products
        User user = new User(UserType.AFFILIATE, LocalDate.now().minusYears(1)); // Affiliate for less than 2 years
        List<Product> products = Arrays.asList(
                new Product("Product1", 120.0, false),
                new Product("Product2", 80.0, false)
        );
        Bill bill = new Bill(user, products); // Initialize Bill object with products

        // Calculate net payable amount
        double netPayableAmount = DiscountUtils.calculateNetPayableAmount(bill);

        double expectedAmount = 130.0 + 60.0 - 20.0;
        assertEquals(expectedAmount, netPayableAmount, 0.01);
    }

}
