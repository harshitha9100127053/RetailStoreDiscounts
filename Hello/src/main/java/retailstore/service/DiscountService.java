package retailstore.service;

import retailstore.model.Bill;
import retailstore.util.DiscountCalculator;

public class DiscountService {
    private final DiscountCalculator discountCalculator;

    public DiscountService() {
        this.discountCalculator = new DiscountCalculator();
    }

    public double calculateNetPayableAmount(Bill bill) {
        double totalAmount = bill.getProducts().stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
        double percentageDiscount = discountCalculator.calculatePercentageDiscount(bill.getUser(), bill.getProducts());
        double flatDiscount = discountCalculator.calculateFlatDiscount(totalAmount);

        return totalAmount - percentageDiscount - flatDiscount;
    }
}
