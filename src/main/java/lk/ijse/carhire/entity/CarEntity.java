package lk.ijse.carhire.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Car")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID")
    private long carID;
    @Column(name = "VehicleNumber")
    private String vehicleNumber;
    @Column(name = "Brand")
    private String brand;
    @Column(name = "Model")
    private String model;
    @Column(name = "Year")
    private int year;
    @Column(name = "DailyRate")
    private double dailyRate;
    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private CarCategoryEntity carCategory;
}
