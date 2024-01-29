package lk.ijse.carhire.dto.tablemodel;

import lk.ijse.carhire.dto.CarDTO;
import lk.ijse.carhire.dto.CustomerDTO;

import java.time.LocalDate;

public class RentListTable {
    private long rentId;
    private long carID;
    private long customerID;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double rentFee;

    public RentListTable(
            long rentId,
            long carId,
            long customerId,
            LocalDate rentDate,
            LocalDate returnDate,
            Double rentFee) {
    }

}
