package retailstore.service;

import org.springframework.stereotype.Service;
import retailstore.model.Bill;
import retailstore.util.DiscountUtils;

@Service
public class DiscountCalculatorServiceImpl implements DiscountCalculatorService {

    @Override
    public double calculateNetPayableAmount(Bill bill) {
        return DiscountUtils.calculateNetPayableAmount(bill);
    }
}
