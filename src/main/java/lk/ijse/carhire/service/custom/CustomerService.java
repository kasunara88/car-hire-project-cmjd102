package lk.ijse.carhire.service.custom;

import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {
    void customerSaved(CustomerDTO customerDTO) throws Exception;
    void customerUpdate(CustomerDTO customerDTO) throws Exception;
    void customerDelete(CustomerDTO customerID) throws Exception;
    CustomerDTO customerSearch(long customerID) throws Exception;
    List<CustomerDTO> getAllCustomer() throws Exception;
}
