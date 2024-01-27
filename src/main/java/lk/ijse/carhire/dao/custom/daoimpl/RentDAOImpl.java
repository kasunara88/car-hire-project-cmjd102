package lk.ijse.carhire.dao.custom.daoimpl;

import lk.ijse.carhire.dao.custom.RentDAO;
import lk.ijse.carhire.dao.custom.exception.DataAccessException;
import lk.ijse.carhire.entity.CustomerEntity;
import lk.ijse.carhire.entity.RentEntity;
import lk.ijse.carhire.utill.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class RentDAOImpl implements RentDAO {
    @Override
    public void save(RentEntity rentEntity) throws Exception {
        Transaction transaction = null;
        try(Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(rentEntity);
            transaction.commit();
        }catch (ConstraintViolationException e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Duplicate entry: " + e.getMessage());
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Error saving rent: " + e.getMessage(), e);
        }

    }

    @Override
    public void update(RentEntity rentEntity) throws Exception {

    }

    @Override
    public void delete(RentEntity rentEntity) throws Exception {

    }

    @Override
    public RentEntity search(Long s) throws Exception {
        return null;
    }

    @Override
    public List<RentEntity> getAll() throws Exception {
        Transaction transaction = null;
        try(Session session= SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            List<RentEntity> rentList = session.createQuery("FROM RentEntity ", RentEntity.class).list();
            transaction.commit();
            return rentList;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            throw new DataAccessException("Error getting all Rents: " + e.getMessage(), e);
        }
    }
}
