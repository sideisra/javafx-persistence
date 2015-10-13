package de.saxsys.javafx.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CarDb {
	private static final String PERSISTENCE_UNIT_NAME = "cars";
	private static EntityManagerFactory factory;

	public CarDb() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public List<Car> readCars() {
		final EntityManager em = factory.createEntityManager();
		try {
			final Query q = em.createQuery("select c from Car c");
			final List<Car> cars = q.getResultList();
			return cars;
		} finally {
			em.close();
		}
	}

	public void saveCar(final Car newCar) {
		final EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(newCar);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void deleteCar(final Car carToDelete) {
		final EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.merge(carToDelete));
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}
