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

 private final EntityManager em = factory.createEntityManager();

 public List<Manufacturer> readManufacturer() {
  final Query q = em.createQuery("select m from Manufacturer m");
  final List<Manufacturer> manufacturer = q.getResultList();
  return manufacturer;
 }

 public void saveManufacturer(final Manufacturer newManufacturer) {
  inTransaction(em -> {
   if (em.contains(newManufacturer)) {
    em.merge(newManufacturer);
   } else {
    em.persist(newManufacturer);
   }
  });
 }

 public void deleteManufacturer(final Manufacturer manufacturerToDelete) {
  inTransaction(em -> em.remove(em.merge(manufacturerToDelete)));
 }

 private void inTransaction(final Consumer<EntityManager> command) {
  em.getTransaction().begin();
  command.accept(em);
  em.getTransaction().commit();
 }
}