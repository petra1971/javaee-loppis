package se.iths.service;

import se.iths.entity.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ItemService {

    @PersistenceContext
    EntityManager entityManager;

    public Item createItem(Item item) {
        entityManager.persist(item);
        return item;
    }

    public void updateItem(Item item) {
        entityManager.merge(item);
    }

    public Item findItemById(Long id) {
        return entityManager.find(Item.class, id);
    }

    // JPQL Dynamic query
    public List<Item> getAllItems() {
        return entityManager.createQuery("SELECT i from Item i", Item.class).getResultList();
    }

    public void deleteItem(Long id) {
        Item itemToRemove = entityManager.find(Item.class, id);
        entityManager.remove(itemToRemove);
    }

    public Item updateName(Long id, String name) {
        Item foundItem = entityManager.find(Item.class, id);
        //Nu finns foundItem i vårt Persistence Context
        foundItem.setName(name);
        // Ändringen registreras i vårt Percistence Context
        return foundItem;
    }

    //JPQL queries

    // Dynamic query - risk for sql injection
    public List<Item> getByCategoryDynamicQuery(String category) {
        return entityManager.createQuery("SELECT i from Item i WHERE i.category = '" + category + "'", Item.class).getResultList();
    }

    //Named query - mer säker
    public List<Item> getByCategoryNamedParameters(String category) {
        String query = "SELECT i from Item i WHERE i.category = :category";
        return entityManager.createQuery(query, Item.class).setParameter("category", category).getResultList();
    }

    //Positional parameters
    public List<Item> getByCategoryPositionalParameters(String category) {
        String query = "SELECT i from Item i WHERE i.category = ?1";
        return entityManager.createQuery(query, Item.class).setParameter(1, category).getResultList();
    }

    //Where between
    public List<Item> getItemsBetweenPrices(double minPrice, double maxPrice) {
        String query = "SELECT i from Item i WHERE i.price BETWEEN :minPrice AND :maxPrice";
        return entityManager.createQuery(query, Item.class).setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice).getResultList();
    }

    // Order by (alphabetically)
    public List<Item> getAllItemsOrderedByCategory() {
        String query = "SELECT i from Item i ORDER BY i.category";
        return entityManager.createQuery(query, Item.class).getResultList();
    }

    // Named query
    public List<Item> getAllItemsNamedQuery() {
        return entityManager.createNamedQuery("itemEntity.findAll", Item.class).getResultList();
    }

    //Typed query-select the highest price from all items
    public double getHighestPrice() {
        TypedQuery<Double> typedQuery = entityManager.createQuery("SELECT MAX(i.price) from Item i", Double.class);
        return typedQuery.getSingleResult();
    }

    // Criteria API queries - typsäkert sätt som underlättar refakturering t ex byte av namn på parameter "name" skulle inte uppfattas av compilatorn eftersom name är en

    // Get all items (no rest-method created)
    public List<Item> getAllCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    // Order by category (no rest-method created)
    public List<Item> getAllItemsSortedByCategoryCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(itemRoot.get("category")));
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }


}
