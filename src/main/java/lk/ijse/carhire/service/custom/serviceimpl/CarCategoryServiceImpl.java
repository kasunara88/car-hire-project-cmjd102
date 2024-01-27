package lk.ijse.carhire.service.custom.serviceimpl;

import com.google.protobuf.ServiceException;
import lk.ijse.carhire.dao.DAOFactory;
import lk.ijse.carhire.dao.custom.CarCategoryDAO;
import lk.ijse.carhire.dao.custom.exception.DataAccessException;
import lk.ijse.carhire.dao.custom.exception.NotFoundException;
import lk.ijse.carhire.dto.CategoryDTO;
import lk.ijse.carhire.entity.CarCategoryEntity;
import lk.ijse.carhire.service.custom.CarCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CarCategoryServiceImpl implements CarCategoryService {
    CarCategoryDAO categoryDAO = (CarCategoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAR_CATEGORY);
    private static final Logger logger = LoggerFactory.getLogger(CarCategoryServiceImpl.class);
    @Override
    public void carCategorySaved(CategoryDTO carCategoryDto) throws Exception {
        try {
            CarCategoryEntity carCategory = new CarCategoryEntity(
                    carCategoryDto.getCategory_id(),
                    carCategoryDto.getCategory_Name());

            categoryDAO.save(carCategory);
        }catch (DataAccessException e){

            e.printStackTrace();
            throw new ServiceException("Error Saving Car Category :" + e.getMessage());
        }

    }

    @Override
    public void carCategoryUpdate(CategoryDTO categoryDTO) throws Exception {
        if(categoryDTO == null){
            throw new IllegalArgumentException("CarCategoryDTO cannot be null");
        }

        long categoryId = categoryDTO.getCategory_id();
        String categoryName = categoryDTO.getCategory_Name();

        // Additional input validation
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid Car Category ID. Please enter a valid number.");
        }

        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Car Category Name. Please enter a valid name.");
        }

        CarCategoryEntity carCategory = new CarCategoryEntity(categoryId, categoryName);
        categoryDAO.update(carCategory);
        logger.info("Car Category updated: {}", carCategory);
    }

    @Override
    public void carCategoryDelete(CategoryDTO categoryID) throws Exception {
        if(categoryID == null){
            throw new IllegalArgumentException("CarCategory ID cannot be null");
        }

        try {
            var categoryIDDelete = new CarCategoryEntity();
            categoryIDDelete.setCategory_ID(categoryID.getCategory_id());
            categoryDAO.delete(categoryIDDelete);
        }catch (Exception e){
            throw new RuntimeException("Error deleting Category ID: " + e.getMessage(), e);
        }
    }

    @Override
    public CategoryDTO search(long categoryID) throws Exception {
        CarCategoryEntity categoryEntity = categoryDAO.search(categoryID);
        if(categoryEntity !=null){
            return new CategoryDTO(
                    categoryEntity.getCategory_ID(),
                    categoryEntity.getCategory_Name());
        }else {
            return null; // Return null if category not found
        }
    }

    @Override
    public List<CategoryDTO> getAllCarCategory() throws Exception {
        List<CategoryDTO> carCategory = new ArrayList<>();
        try {
            List<CarCategoryEntity> carCategoryEntities = categoryDAO.getAll();
            for (CarCategoryEntity entity: carCategoryEntities) {
                CategoryDTO categoryDto = new CategoryDTO(entity.getCategory_ID(), entity.getCategory_Name());
                carCategory.add(categoryDto);}
        }catch (DataAccessException e){
            logger.error("Error getting all car categories", e);
            throw new ServiceException("Error getting all car categories", e);
        }
        return carCategory;
    }
    }






