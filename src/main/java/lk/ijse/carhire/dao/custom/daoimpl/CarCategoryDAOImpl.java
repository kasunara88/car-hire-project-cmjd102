package lk.ijse.carhire.dao.custom.daoimpl;

import lk.ijse.carhire.dao.custom.CarCategoryDAO;
import lk.ijse.carhire.dao.custom.exception.DataAccessException;
import lk.ijse.carhire.dao.custom.exception.NotFoundException;
import lk.ijse.carhire.entity.CarCategoryEntity;
import lk.ijse.carhire.utill.SessionFactoryConfiguration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CarCategoryDAOImpl implements CarCategoryDAO {
    private static final Logger logger = LoggerFactory.getLogger(CarCategoryDAOImpl.class);
    @Override
    public void save(CarCategoryEntity carCategoryEntity) throws Exception {
        Transaction transaction = null;
        try(Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(carCategoryEntity);
            transaction.commit();
        }catch (ConstraintViolationException e){
           if(transaction != null && transaction.isActive()){
               transaction.rollback();
           }
            throw new RuntimeException("Duplicate entry: " + e.getMessage());
        }catch (HibernateException e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new DataAccessException("Error saving Car Category: " + e.getMessage(), e);
        }

    }

    @Override
    public void update(CarCategoryEntity carCategoryEntity) throws Exception {
        Transaction transaction = null;
        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction();
                session.merge(carCategoryEntity);
                transaction.commit();
                logger.info("Car Category updated: {}", carCategoryEntity);
            } catch (ConstraintViolationException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                logger.error("Error updating Car Category due to constraint violation: {}", e.getMessage(), e);
                throw new DataAccessException("Error updating Car Category: " + e.getMessage(), e);
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                logger.error("Error updating Car Category: {}", e.getMessage(), e);
                throw new DataAccessException("Error updating Car Category: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void delete(CarCategoryEntity carCategoryEntity) throws Exception {
        Transaction transaction = null;
        if(carCategoryEntity == null){
            throw new IllegalArgumentException("CategoryEntity cannot be null");
        }

        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(carCategoryEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Error deleting CategoryID: " + e.getMessage(), e);
        }

    }

    @Override
    public CarCategoryEntity search(Long id) throws Exception {
        try (Session session = SessionFactoryConfiguration.getSessionFactory().openSession()){
            var carCategoryID = session.get(CarCategoryEntity.class, id);
            if(carCategoryID == null){
                throw new NotFoundException("Car Category with ID " + id + " not found");
            }
            return carCategoryID;
        } catch (Exception e){
            throw new DataAccessException("Error searching for Car Category: " + e.getMessage(), e);
        }
        }

    @Override
    public List<CarCategoryEntity> getAll() throws Exception {

        try(Session session=SessionFactoryConfiguration.getSessionFactory().openSession()){
            return session.createQuery("FROM CarCategoryEntity", CarCategoryEntity.class).list();
        }catch (HibernateException e){

            throw new DataAccessException("Error getting all Car Category: " + e.getMessage(), e);
        }
    }
    }


