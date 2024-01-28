package lk.ijse.carhire.dao.custom.daoimpl;

import lk.ijse.carhire.dao.custom.CarDAO;
import lk.ijse.carhire.dao.custom.exception.DataAccessException;
import lk.ijse.carhire.dao.custom.exception.DuplicateEntryException;
import lk.ijse.carhire.dao.custom.exception.NotFoundException;
import lk.ijse.carhire.entity.CarEntity;
import lk.ijse.carhire.entity.CustomerEntity;
import lk.ijse.carhire.utill.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class CarDAOImpl implements CarDAO {
    @Override
    public void save(CarEntity carEntity) throws Exception {
        Transaction transaction = null;
        try(Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(carEntity);
            transaction.commit();
        }catch (ConstraintViolationException e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DuplicateEntryException("Duplicate entry: " + e.getMessage(), e);

//            transaction.rollback();
//            throw new RuntimeException("Duplicate entry: " + e.getMessage());
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Error saving car: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(CarEntity carEntity) throws Exception {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction();
                session.update(carEntity);
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
    public void delete(CarEntity carEntityID) throws Exception {
        Transaction transaction = null;
        if(carEntityID == null){
            throw new IllegalArgumentException("CaEntityID cannot be null");
        }

        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(carEntityID);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Error deleting customer: " + e.getMessage(), e);
        }
    }


    @Override
    public CarEntity search(Long id) throws Exception {
        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            var carID = session.get(CarEntity.class, id);
            if(carID == null){
                throw new NotFoundException("Customer with ID " + id + " not found");
            }
            return carID;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<CarEntity> getAll() throws Exception {
        Transaction transaction = null;
        try(Session session=SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            List<CarEntity> cars = session.createQuery("FROM CarEntity ", CarEntity.class).list();
            transaction.commit();
            return cars;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            throw new DataAccessException("Error getting all cars: " + e.getMessage(), e);
        }
  }
    }
