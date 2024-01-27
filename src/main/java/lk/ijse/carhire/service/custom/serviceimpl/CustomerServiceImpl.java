package lk.ijse.carhire.service.custom.serviceimpl;

import lk.ijse.carhire.dao.DAOFactory;
import lk.ijse.carhire.dao.custom.CustomerDAO;
import lk.ijse.carhire.dto.CustomerDTO;
import lk.ijse.carhire.entity.CustomerEntity;
import lk.ijse.carhire.service.custom.CustomerService;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public void customerSaved(CustomerDTO customerDTO) throws Exception {
        try {
            CustomerEntity customerEntity = new CustomerEntity(
                    customerDTO.getCustomerId(),
                    customerDTO.getTitle(),
                    customerDTO.getFirstName(),
                    customerDTO.getLastName(),
                    customerDTO.getCity(),
                    customerDTO.getAddress(),
                    customerDTO.getPostalCode(),
                    customerDTO.getEmail(),
                    customerDTO.getContactNumber());

            customerDAO.save(customerEntity);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error Saving Customer :" + e.getMessage());
        }

    }

    @Override
    public void customerUpdate(CustomerDTO customerDTO) throws Exception {
        if (customerDTO == null){
            throw new IllegalArgumentException("CustomerDto cannot be null");
        }
        CustomerEntity customerEntity = new CustomerEntity(customerDTO.getCustomerId(), customerDTO.getTitle(),
                customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getCity(), customerDTO.getAddress(),
                customerDTO.getPostalCode(), customerDTO.getEmail(), customerDTO.getContactNumber());
        customerDAO.update(customerEntity);

    }

    @Override
    public void customerDelete(CustomerDTO customerID) throws Exception {
        if(customerID == null){
            throw new IllegalArgumentException("CustomerDto cannot be null");
        }

        try {
            var customerDelete = new CustomerEntity();
            customerDelete.setCustID(customerID.getCustomerId());
            customerDAO.delete(customerDelete);
        }catch (Exception e){
            throw new RuntimeException("Error deleting customer: " + e.getMessage(), e);
        }
    }

    @Override
    public CustomerDTO customerSearch(long customerID) throws Exception {
        CustomerEntity customerEntity = customerDAO.search(customerID);
        return new CustomerDTO(
                customerEntity.getCustID(),
                customerEntity.getTitle(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getCity(),
                customerEntity.getAddress(),
                customerEntity.getPostalCode(),
                customerEntity.getEmail(),
                customerEntity.getContactNumber());
    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws Exception {
        List<CustomerDTO> customerDtos = new ArrayList<>();
        List<CustomerEntity> customerEntities = customerDAO.getAll();
        for(CustomerEntity entity : customerEntities){
            CustomerDTO customerDto = new CustomerDTO(
                    entity.getCustID(),
                    entity.getTitle(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getCity(),
                    entity.getAddress(),
                    entity.getPostalCode(),
                    entity.getEmail(),
                    entity.getContactNumber());
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }
}
