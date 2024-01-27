package lk.ijse.carhire.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CarDTO {
    private long carId;
    private  String vehicleNumber;
    private  String brand;
    private  String model;
    private  int year;
    private  double rate;
    private CategoryDTO categoryDto;

    public CarDTO(
            long carID,
            String vehicleNumber,
            String brand,
            String model,
            int year,
            double dailyRate) {
    }

}
