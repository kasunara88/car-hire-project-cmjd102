package lk.ijse.carhire.dto.tablemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarTableModel {
    private long carId;
    private  String vehicleNumber;
    private  String brand;
    private  String model;
    private  int year;
    private  double rate;
}
