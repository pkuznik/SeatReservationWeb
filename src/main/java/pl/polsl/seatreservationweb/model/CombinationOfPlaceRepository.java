
package pl.polsl.seatreservationweb.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * This class is repositorium for CombinationOfPlace entitiy.
 * This class can read, save and find entity
 *
 * @author Piotr Kuźnik
 * @version 5.1
 */
public class CombinationOfPlaceRepository {

    /**
     * EntityManager
     */
    private final EntityManager entityManager;
    
    /**
     * Constructor
     * 
     * @param entityManager object instance of EntityManager
     * @throws SeatReservationException throw when connection is closed
     */
    public CombinationOfPlaceRepository(EntityManager entityManager) throws SeatReservationException {
        if (!entityManager.isOpen()) {
            throw new SeatReservationException("Połączenie do bazy jest zamknięte!");
        }
        
        this.entityManager = entityManager;
    }
    
    /**
     * This method save object in database
     * 
     * @param object of CombinationOfPlace
     * @throws SeatReservationException when entity manager cannot save object in db 
     */
    public void add(CombinationOfPlace object) throws SeatReservationException{
        
        this.entityManager.getTransaction().begin();
        try {
            this.entityManager.persist(object);
            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
          
            throw new SeatReservationException("Wystąpił problem podczas zapisu encji do bazy: " + e.getMessage());
        } 
    }
    
    /**
     * 
     * @return List of entities type CombinationOfPlace
     */
    public List<CombinationOfPlace> getAll() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CombinationOfPlace> cq = builder.createQuery(CombinationOfPlace.class);
        Root<CombinationOfPlace> rootEntry = cq.from(CombinationOfPlace.class);
        CriteriaQuery<CombinationOfPlace> all = cq.select(rootEntry);
        TypedQuery<CombinationOfPlace> allQuery = this.entityManager.createQuery(all);
        return allQuery.getResultList();
    }
    
    
    /**
     * Truncate all data of CombinationOfPlace
     */
    public void removeAll() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<CombinationOfPlace> query = builder.createCriteriaDelete(CombinationOfPlace.class);
        query.from(CombinationOfPlace.class);
        this.entityManager.createQuery(query).executeUpdate();
    }
    
    /**
     * Close connection to DB
     */
    public void closeConnection() {
         this.entityManager.close();
    }
}
