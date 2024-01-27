package lk.ijse.carhire.dao.custom.daoimpl;

import lk.ijse.carhire.dao.custom.CustomerDAO;
import lk.ijse.carhire.dao.custom.exception.DataAccessException;
import lk.ijse.carhire.dao.custom.exception.NotFoundException;
import lk.ijse.carhire.entity.CustomerEntity;
import lk.ijse.carhire.utill.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void save(CustomerEntity customerEntity) throws Exception {
        Transaction transaction = null;
        try(Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(customerEntity);
            transaction.commit();
        }catch (ConstraintViolationException e){
            transaction.rollback();
            throw new RuntimeException("Duplicate entry: " + e.getMessage());
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void update(CustomerEntity customerEntity) throws Exception {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction();
                session.merge(customerEntity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new DataAccessException("Error updating customer: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void delete(CustomerEntity customerID) throws Exception {
        Transaction transaction = null;
        if(customerID == null){
            throw new IllegalArgumentException("CustomerEntity cannot be null");
        }

        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(customerID);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Error deleting customer: " + e.getMessage(), e);
        }
    }

    @Override
    public CustomerEntity search(Long id) throws Exception {
        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            var customerID = session.get(CustomerEntity.class, id);
            if(customerID == null){
                throw new NotFoundException("Customer with ID " + id + " not found");
            }
            return customerID;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<CustomerEntity> getAll() throws Exception {
        Transaction transaction = null;
        try(Session session=SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            List<CustomerEntity> customers = session.createQuery("FROM CustomerEntity", CustomerEntity.class).list();
            transaction.commit();
            return customers;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            throw new DataAccessException("Error getting all customers: " + e.getMessage(), e);
        }
    }
}
