package lk.ijse.carhire.service.custom.serviceimpl;

import lk.ijse.carhire.dao.DAOFactory;
import lk.ijse.carhire.dao.custom.CarCategoryDAO;
import lk.ijse.carhire.dao.custom.CarDAO;
import lk.ijse.carhire.dto.CarDTO;
import lk.ijse.carhire.dto.CategoryDTO;
import lk.ijse.carhire.entity.CarCategoryEntity;
import lk.ijse.carhire.entity.CarEntity;
import lk.ijse.carhire.service.custom.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarSericeImpl implements CarService {
    private CarDAO carDAO = (CarDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAR);
    private CarCategoryDAO categoryDAO = (CarCategoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAR_CATEGORY);
    private static final Logger logger = LoggerFactory.getLogger(CarSericeImpl.class);

    @Override
    public void carSaved(CarDTO carDTO) throws Exception {
        try {
            CarCategoryEntity categoryEntity = convertToCarCategoryEntity(carDTO.getCategoryDto());
            var carEntity = new CarEntity(
                    carDTO.getCarId(),
                    carDTO.getVehicleNumber(),
                    carDTO.getBrand(),
                    carDTO.getModel(),
                    carDTO.getYear(),
                    carDTO.getRate(), categoryEntity);
            carDAO.save(carEntity);
        } catch (NumberFormatException e) {
            logger.error("Error converting numeric values: {}", e.getMessage());
            throw new RuntimeException("Error Saving Car: Invalid numeric values");
        } catch (Exception e) {
            logger.error("Error Saving Car: {}", e.getMessage(), e);
            throw new RuntimeException("Error Saving Car: " + e.getMessage(), e);
        }
    }

    @Override
    public void carUpdate(CarDTO carDTO) throws Exception {
        if (carDTO == null) {
            throw new IllegalArgumentException("CarDt0 cannot be null");
        }
        CarEntity carEntity = new CarEntity();
        carEntity.setCarID(carDTO.getCarId());
        //carEntity.setCarCategory(convertToCarCategoryEntity(carDTO.getCategoryDto()));
        carEntity.setVehicleNumber(carDTO.getVehicleNumber());
        carEntity.setBrand(carDTO.getBrand());
        carEntity.setModel(carDTO.getModel());
        carEntity.setYear(carDTO.getYear());
        carEntity.setDailyRate(carDTO.getRate());
        carDAO.update(carEntity);
    }

    @Override
    public void carDelete(CarDTO carID) throws Exception {
        if (carID == null) {
            throw new IllegalArgumentException("CarID cannot be null");
        }
        try {
            var carrDelete = new CarEntity();
            carrDelete.setCarID(carID.getCarId());
            carDAO.delete(carrDelete);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting customer: " + e.getMessage(), e);
        }
    }

    @Override
    public CarDTO carSearch(long carID) throws Exception {
        CarEntity carEntityID = carDAO.search(carID);
        CarDTO carList = new CarDTO();
        carList.setCarId(carEntityID.getCarID());
        carList.setVehicleNumber(carEntityID.getVehicleNumber());
        carList.setBrand(carEntityID.getBrand());
        carList.setModel(carEntityID.getModel());
        carList.setYear(carEntityID.getYear());
        carList.setRate(carEntityID.getDailyRate());
        return carList;
    }

    @Override
    public List<CarDTO> getAllCars() throws Exception {
        List<CarDTO> carList = new ArrayList<>();
        List<CarEntity> carEntityList = carDAO.getAll();
        for (CarEntity entity : carEntityList) {
            CarDTO carDtoList = new CarDTO();
            carDtoList.setCarId(entity.getCarID());
            carDtoList.setCategoryDto(convertToCarCategoryDTO(entity.getCarCategory()));
            carDtoList.setVehicleNumber(entity.getVehicleNumber());
            carDtoList.setBrand(entity.getBrand());
            carDtoList.setModel(entity.getModel());
            carDtoList.setYear(entity.getYear());
            carDtoList.setRate(entity.getDailyRate());
            carList.add(carDtoList);
        }
        return carList;

    }

    private CarCategoryEntity convertToCarCategoryEntity(CategoryDTO categoryDto) {
        CarCategoryEntity carCategoryEntity = new CarCategoryEntity();
        carCategoryEntity.setCategory_ID(categoryDto.getCategory_id());
        carCategoryEntity.setCategory_Name(categoryDto.getCategory_Name());
        return carCategoryEntity;
    }

    private CategoryDTO convertToCarCategoryDTO(CarCategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategory_id(categoryEntity.getCategory_ID());
        //categoryDTO.setCategory_Name(categoryEntity.getCategory_Name());
        return categoryDTO;
    }
}
