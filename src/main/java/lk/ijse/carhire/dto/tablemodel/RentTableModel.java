package lk.ijse.carhire.dto.tablemodel;

import lk.ijse.carhire.dto.CarDTO;
import lk.ijse.carhire.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentTableModel {
    private long rentId;
    private long customerID;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double rentFee;
}
