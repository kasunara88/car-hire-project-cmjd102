package lk.ijse.carhire.service.custom;

import lk.ijse.carhire.dto.CarDTO;
import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.service.SuperService;

import java.util.List;

public interface CarService extends SuperService {
    void carSaved(CarDTO carDTO) throws Exception;
    void carUpdate(CarDTO carDTO) throws Exception;
    void carDelete(CarDTO carID) throws Exception;
    CarDTO carSearch(long carID) throws Exception;
    List<CarDTO> getAllCars() throws Exception;
}
