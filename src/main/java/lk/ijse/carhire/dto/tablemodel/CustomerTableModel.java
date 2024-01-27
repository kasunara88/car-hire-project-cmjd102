package lk.ijse.carhire.dto.tablemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerTableModel {
    private long customerId;
    private String title;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String postal_code;
    private String email;
    private String contact_Number;
}
