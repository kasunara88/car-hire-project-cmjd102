package lk.ijse.carhire.service.custom.serviceimpl;

import lk.ijse.carhire.dao.DAOFactory;
import lk.ijse.carhire.dao.custom.CarDAO;
import lk.ijse.carhire.dao.custom.CustomerDAO;
import lk.ijse.carhire.dao.custom.RentDAO;
import lk.ijse.carhire.dto.CarDTO;

import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.dto.RentDTO;

import lk.ijse.carhire.entity.CarEntity;
import lk.ijse.carhire.entity.CustomerEntity;
import lk.ijse.carhire.entity.RentEntity;
import lk.ijse.carhire.service.custom.RentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentServiceImpl implements RentService {
    RentDAO rentDAO = (RentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RENT);
    CarDAO carDAO = (CarDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAR);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    @Override
    public void rentSaved(RentDTO rentDto) throws Exception {
        try {
            CarEntity carID = carDAO.search(rentDto.getCarID().getCarId());
            CustomerEntity custID = customerDAO.search(rentDto.getCustomerID().getCustomerId());
           // CarEntity carID = convertToCarEntity(rentDto.getCarID());
//            CustomerEntity customerEntity = convertToCustomerEntity(rentDto.getCustomerID());
//
//            saveCarAndCustomer(carEntity, customerEntity);

            RentEntity rentEntity = new RentEntity(
                    rentDto.getRentId(),
                    carID,
                    custID,
                    rentDto.getRentDate(),
                    rentDto.getReturnDate(),
                    rentDto.getRentFee());
            rentDAO.save(rentEntity);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error Saving Customer :" + e.getMessage());
        }
    }

    private void saveCarAndCustomer(CarEntity carEntity, CustomerEntity customerEntity) throws Exception {
        try {
            carDAO.save(carEntity);
            customerDAO.save(customerEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Saving Customer: " + e.getMessage());
        }
    }

    @Override
    public void rentUpdate(RentDTO rentDto) throws Exception {

    }

    @Override
    public void rentDelete(RentDTO rentID) throws Exception {

    }

    @Override
    public RentDTO search(long rentID) throws Exception {
        return null;
    }

    @Override
    public List<RentDTO> getAllRent() throws Exception {
                List<RentDTO> rentList = new ArrayList<>();
        List<RentEntity> rentEntityList = rentDAO.getAll();
        for (RentEntity entity : rentEntityList) {
            RentDTO rentDtoList = new RentDTO();
            rentDtoList.setRentId(entity.getRentalID());
            rentDtoList.setCarID(convertToCarIDs(entity.getCarEntity()));
            rentDtoList.setCustomerID(convertToCustomerIDs(entity.getCustomerEntity()));
            rentDtoList.setRentDate(entity.getRentalStartDate());
            rentDtoList.setReturnDate(entity.getRentalEndDate());
            rentDtoList.setRentFee(entity.getRentalFee());
            rentList.add(rentDtoList);
        }
        return rentList;
    }

   @Override
   public void returnCar(long rentId, LocalDate returnDate) throws Exception{
        if (rentId <= 0) {
            throw new IllegalArgumentException("Invalid rent ID. Rent ID must be greater than 0.");
        }

        // Retrieve the Rent entity from the database
        RentEntity rentEntity = rentDAO.search(rentId);
                 new Exception("Rent not found with ID: " + rentId);

        // Update the return date
        rentEntity.setRentalEndDate(returnDate);
        rentDAO.save(rentEntity);
   }

    private CustomerDTO convertToCustomerIDs (CustomerEntity custDto) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(custDto.getCustID());
        return customerDTO;
    }

    private CarDTO convertToCarIDs (CarEntity carDto) {
        CarDTO carDTO = new CarDTO();
        carDTO.setCarId(carDTO.getCarId());
        return carDTO;
    }
}
