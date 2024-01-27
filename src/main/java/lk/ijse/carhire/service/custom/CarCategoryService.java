package lk.ijse.carhire.service.custom;

import lk.ijse.carhire.dao.custom.CarCategoryDAO;
import lk.ijse.carhire.dto.CategoryDTO;
import lk.ijse.carhire.service.SuperService;

import java.util.List;

public interface CarCategoryService extends SuperService {
    void carCategorySaved(CategoryDTO carCategoryDto) throws Exception;

    void carCategoryUpdate(CategoryDTO custDto) throws Exception;

    void carCategoryDelete(CategoryDTO customerDtoID) throws Exception;

    CategoryDTO search(long custID) throws Exception;
    List<CategoryDTO> getAllCarCategory() throws Exception;
}
