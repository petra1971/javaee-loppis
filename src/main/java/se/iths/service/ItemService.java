package se.iths.service;

import se.iths.entity.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ItemService {

    @PersistenceContext
    EntityManager entityManager;

    public void createItem(Item item) {
        entityManager.persist(item);
    }

    public void updateItem(Item item) {
        entityManager.merge(item);
    }

    public Item findItemById(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> getAllItems() {
        return entityManager.createQuery("SELECT i from Item i", Item.class).getResultList();
    }

    public void deleteItem(Long id) {
        Item itemToRemove = entityManager.find(Item.class, id)
        entityManager.remove(itemToRemove);
    }
}
