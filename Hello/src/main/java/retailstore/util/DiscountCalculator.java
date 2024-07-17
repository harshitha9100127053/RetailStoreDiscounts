package retailstore.util;


import org.springframework.stereotype.Component;
import retailstore.model.Product;
import retailstore.model.ProductCategory;
import retailstore.model.User;
import retailstore.model.UserType;

import java.time.LocalDate;
import java.util.List;

@Component
public class DiscountCalculator {

    public double calculatePercentageDiscount(User user, List<Product> products) {
        double totalDiscount = 0.0;
        double percentageDiscount = 0.0;

        // Calculate percentage discount based on user type
        if (user.getUserType() == UserType.EMPLOYEE) {
            percentageDiscount = 0.30; // 30%
        } else if (user.getUserType() == UserType.AFFILIATE) {
            percentageDiscount = 0.10; // 10%
        } else if (user.getUserType() == UserType.CUSTOMER && user.getCustomerSince().isBefore(LocalDate.now().minusYears(2))) {
            percentageDiscount = 0.05; // 5% for customers over 2 years
        }

        // Apply percentage discount only to non-grocery items
        for (Product product : products) {
            if (product.getCategory() != ProductCategory.GROCERIES) {
                totalDiscount += product.getPrice() * percentageDiscount;
            }
        }

        return totalDiscount;
    }

    public double calculateFlatDiscount(double totalAmount) {
        // Calculate flat discount for every $100
        return Math.floor(totalAmount / 100) * 5;
    }
}
