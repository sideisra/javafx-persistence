package de.saxsys.javafx.persistence;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CarDb {
 private static final String PERSISTENCE_UNIT_NAME = "cars";
 private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

 public List<Manufacturer> readManufacturer() {
  final EntityManager em = factory.createEntityManager();
  try {
   final Query q = em.createQuery("select m from Manufacturer m");
   final List<Manufacturer> manufacturer = q.getResultList();
   return manufacturer;
  } finally {
   em.close();
  }
 }

 public void saveManufacturer(final Manufacturer newManufacturer) {
  if (newManufacturer.getId() == null) {
   inTransaction(em -> em.persist(newManufacturer));
  } else {
   inTransaction(em -> em.merge(newManufacturer));
  }
 }

 public void deleteManufacturer(final Manufacturer manufacturerToDelete) {
  inTransaction(em -> em.remove(em.merge(manufacturerToDelete)));
 }

 private void inTransaction(final Consumer<EntityManager> command) {
  final EntityManager em = factory.createEntityManager();
  try {
   em.getTransaction().begin();
   command.accept(em);
   em.getTransaction().commit();
  } finally {
   em.close();
  }
 }
}