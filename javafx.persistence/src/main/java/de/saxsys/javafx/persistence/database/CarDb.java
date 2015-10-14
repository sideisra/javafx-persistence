package de.saxsys.javafx.persistence.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import de.saxsys.javafx.persistence.model.Car;

public class CarDb {
	private static final String PERSISTENCE_UNIT_NAME = "cars";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	Mapper mapper = new DozerBeanMapper();
	
	public List<Car> readCars() {
		final EntityManager em = factory.createEntityManager();
		try {
			final Query q = em.createQuery("select c from CarEntity c");
			final List<CarEntity> carEntities = q.getResultList();
			ArrayList<Car> cars = DozerHelper.map(mapper, carEntities, Car.class);
			return cars;
		} finally {
			em.close();
		}
	}
	
	public void saveCar(final Car newCar) {
		
		CarEntity carEntity =
				mapper.map(newCar, CarEntity.class);
		
		final EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(carEntity);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	public void deleteCar(final Car carToDelete) {
		
		CarEntity carEntity =
				mapper.map(carToDelete, CarEntity.class);
		
		final EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.merge(carEntity));
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}
