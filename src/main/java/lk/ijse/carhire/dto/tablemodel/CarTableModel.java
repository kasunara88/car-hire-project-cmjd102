package lk.ijse.carhire.dto.tablemodel;

import lk.ijse.carhire.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarTableModel {
    private long carId;
    private long categoryID;
    private  String vehicleNumber;
    private  String brand;
    private  String model;
    private  int year;
    private  double rate;
}
