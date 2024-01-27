package lk.ijse.carhire.dto;

import lk.ijse.carhire.entity.CustomerEntity;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RentDTO {
    private long rentId;
    private CarDTO carID;
    private CustomerDTO customerID;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private Double rentFee;

    public RentDTO(long rentalID,
                   CustomerEntity customerEntity,
                   LocalDate rentalStartDate,
                   LocalDate rentalEndDate,
                   double rentalFee) {
    }
}
