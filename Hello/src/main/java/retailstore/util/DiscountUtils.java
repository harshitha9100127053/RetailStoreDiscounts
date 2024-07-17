package retailstore.util;

import retailstore.model.Bill;
import retailstore.model.Product;
import retailstore.model.User;
import retailstore.model.UserType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static retailstore.constants.ApplicationConstants.*;

public class DiscountUtils {

    public static double calculateNetPayableAmount(Bill bill) {
        double totalAmount = calculateTotalAmountExcludingGroceries(bill.getProducts());
        double discountAmount = calculateDiscountAmount(bill);
        return totalAmount - discountAmount;
    }

    private static double calculateTotalAmountExcludingGroceries(List<Product> products) {
        return products.stream()
                .filter(product -> !product.isGrocery())
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private static double calculateDiscountAmount(Bill bill) {
        double totalAmount = calculateTotalAmountExcludingGroceries(bill.getProducts());
        double discount = 0.0;

        for (Product product : bill.getProducts()) {
            if (!product.isGrocery()) { // Exclude groceries from percentage discounts
                double productPrice = product.getPrice();
                User user = bill.getUser();

                if (user.getUserType() == UserType.EMPLOYEE) {
                    discount += productPrice * EMPLOYEE_DISCOUNT_RATE;
                } else if (user.getUserType() == UserType.AFFILIATE) {
                    discount += productPrice * AFFILIATE_DISCOUNT_RATE;
                } else if (user.getUserType() == UserType.CUSTOMER && isLongTermCustomer(user.getCustomerSince())) {
                    discount += productPrice * LONG_TERM_CUSTOMER_DISCOUNT_RATE;
                }
            }
        }

        discount += Math.floor(totalAmount / 100) * DISCOUNT_PER_HUNDRED;

        return discount;
    }

    private static boolean isLongTermCustomer(LocalDate customerSince) {
        return customerSince != null && ChronoUnit.YEARS.between(customerSince, LocalDate.now()) >= 2;
    }
}
