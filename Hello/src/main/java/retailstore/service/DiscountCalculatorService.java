package retailstore.service;

import retailstore.model.Bill;

public interface DiscountCalculatorService {
    double calculateNetPayableAmount(Bill bill);
}
