package lk.ijse.carhire.service.custom;

import lk.ijse.carhire.dao.custom.CarCategoryDAO;
import lk.ijse.carhire.dao.custom.RentDAO;
import lk.ijse.carhire.dto.RentDTO;
import lk.ijse.carhire.service.SuperService;

import java.time.LocalDate;
import java.util.List;

public interface RentService extends SuperService {
    void rentSaved(RentDTO rentDto) throws Exception;

    void rentUpdate(RentDTO rentDto) throws Exception;

    void rentDelete(RentDTO rentID) throws Exception;

    RentDTO search(long rentID) throws Exception;
    List<RentDTO> getAllRent() throws Exception;

    void returnCar(long rentId, LocalDate returnDate) throws Exception;
}
