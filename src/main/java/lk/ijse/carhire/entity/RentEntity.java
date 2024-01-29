package lk.ijse.carhire.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Rental")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentalID")
    private long rentalID;
    @ManyToOne
    @JoinColumn(name = "CarID")
    private CarEntity carEntity;
    @ManyToOne
    @JoinColumn(name = "CustID")
    private CustomerEntity customerEntity;
    @Column(name = "RentalStartDate")
    @Temporal(TemporalType.DATE)
    private LocalDate rentalStartDate;
    @Column(name = "RentalEndDate")
    @Temporal(TemporalType.DATE)
    private LocalDate rentalEndDate;
    @Column(name = "Rental")
    private double rentalFee;

}
