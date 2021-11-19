package se.iths.service;

import se.iths.entity.Item;
import se.iths.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User createUser(User user) {

        // Adding items for demo purposes
        user.addItem(new Item("Soffa", "Möbler", 1, 500.00));
        user.addItem(new Item("Strumpor", "Kläder", 10, 45.00));
        user.addItem(new Item("Stol", "Möbler", 1, 150.00));
        user.addItem(new Item("Tröja", "Kläder", 1, 190.00));
        user.addItem(new Item("Skivspelare", "Hemelektronik", 1, 1500.00));


        entityManager.persist(user);
        return user;

    }

}
