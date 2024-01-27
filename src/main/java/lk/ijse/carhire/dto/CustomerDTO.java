package lk.ijse.carhire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    private long customerId;
    private String title;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String postalCode;
    private String email;
    private String contactNumber;
}
