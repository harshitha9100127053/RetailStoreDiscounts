package retailstore.model;

import java.time.LocalDate;

public class User {
    private UserType userType;
    private LocalDate customerSince;

    public User(UserType userType, LocalDate customerSince) {
        this.userType = userType;
        this.customerSince = customerSince;
    }

    public UserType getUserType() {
        return userType;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }
}
