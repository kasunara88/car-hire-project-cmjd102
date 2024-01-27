package lk.ijse.carhire.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Car_Category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CarCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private long category_ID;
    @Column(name = "CategoryName")
    private String category_Name;
}
