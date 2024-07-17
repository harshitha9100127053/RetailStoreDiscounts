package retailstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retailstore.exceptions.RetailStoreException;
import retailstore.model.Bill;
import retailstore.service.DiscountCalculatorService;
import retailstore.util.DiscountUtils;

@RestController
@RequestMapping("/api/bill")
public class DiscountController {

    @Autowired
    private DiscountCalculatorService discountCalculatorService;

    @PostMapping("/calculateNetPayableAmount")
    public ResponseEntity<Double> calculateNetPayableAmount(@RequestBody Bill bill) {
        try {
            double netPayableAmount = DiscountUtils.calculateNetPayableAmount(bill);
            return ResponseEntity.ok(netPayableAmount);
        } catch (RetailStoreException e) {
            // Custom application-specific exception handling
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1.0); // Example response for custom exception
        } catch (Exception e) {
            // Default exception handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
