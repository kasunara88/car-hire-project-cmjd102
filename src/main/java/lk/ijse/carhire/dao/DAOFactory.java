package lk.ijse.carhire.dao;

import lk.ijse.carhire.dao.custom.daoimpl.CarCategoryDAOImpl;
import lk.ijse.carhire.dao.custom.daoimpl.CarDAOImpl;
import lk.ijse.carhire.dao.custom.daoimpl.CustomerDAOImpl;
import lk.ijse.carhire.dao.custom.daoimpl.RentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getInstance(){
        if(daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case CUSTOMER :
                return new CustomerDAOImpl();
            case CAR_CATEGORY:
                return new CarCategoryDAOImpl();
            case CAR:
                return new CarDAOImpl();
            case RENT:
                return new RentDAOImpl();
            default: return null;

        }
    }

    public enum DAOType{
        CUSTOMER,CAR_CATEGORY,CAR,RENT
    }
}
