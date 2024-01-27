package lk.ijse.carhire.service;

import lk.ijse.carhire.service.custom.serviceimpl.CarCategoryServiceImpl;
import lk.ijse.carhire.service.custom.serviceimpl.CarSericeImpl;
import lk.ijse.carhire.service.custom.serviceimpl.CustomerServiceImpl;
import lk.ijse.carhire.service.custom.serviceimpl.RentServiceImpl;

public class ServiceFactory {
    private  static  ServiceFactory serviceFactory;

    private ServiceFactory() {}

    public static ServiceFactory getInstance(){
        if(serviceFactory == null){
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
}

    public SuperService getService(ServiceType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerServiceImpl();

            case CAR_CATEGORY:
                return new CarCategoryServiceImpl() {
                };
            case CAR:
                return new CarSericeImpl();
            case RENT:
                return new RentServiceImpl();

            default:
                return null;

        }
    }
        public enum ServiceType{
            CUSTOMER,CAR_CATEGORY,CAR,RENT
        }
}
