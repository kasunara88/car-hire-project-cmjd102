package lk.ijse.carhire.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CustID")
    private long custID;
    @Column(name = "Title")
    private String title;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "City")
    private String city;
    @Column(name = "Address")
    private String address;
    @Column(name = "PostalCode")
    private String postalCode;
    @Column(name = "Email")
    private String email;
    @Column(name = "ContactNumber")
    private String contactNumber;
}
