package retailstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retailstore.model.Bill;
import retailstore.service.DiscountService;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController() {
        this.discountService = new DiscountService();
    }

    @PostMapping("/calculate")
    public ResponseEntity<Double> getNetPayableAmount(@RequestBody Bill bill) {
        double netPayableAmount = discountService.calculateNetPayableAmount(bill);
        return ResponseEntity.ok(netPayableAmount);
    }
}
