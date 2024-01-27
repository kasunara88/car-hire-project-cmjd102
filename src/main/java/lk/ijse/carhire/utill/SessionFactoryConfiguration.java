package lk.ijse.carhire.utill;

import lk.ijse.carhire.entity.CarCategoryEntity;
import lk.ijse.carhire.entity.CarEntity;
import lk.ijse.carhire.entity.CustomerEntity;
import lk.ijse.carhire.entity.RentEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryConfiguration {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static  SessionFactory buildSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
//        .configure("org/hibernate/example/hibernate.cfg.xml")
                .loadProperties("/cfg/application.properties")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(CarCategoryEntity.class)
               .addAnnotatedClass(CarEntity.class)
               .addAnnotatedClass(RentEntity.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
                .build();
        return sessionFactory;

    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
