package se.iths.util;

import se.iths.entity.Item;
import se.iths.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class SampleDataGenerator {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void generateData() {

        User user1 = new User("kalle_anka", "kalle@ankeborg.se");
        User user2 = new User("klarabella_ko", "klarabella@ankeborg.se");
        User user3 = new User("a_lukas", "a_lukas@ankeborg.se");

        Item item1 = new Item("Soffa", "Möbler", 1, 150.00);
        Item item2 = new Item("DVD-spelare", "Hemelektronik", 1, 50.00);
        Item item3 = new Item("Krig och fred", "Böcker", 1, 75.00);
        Item item4 = new Item("Kattlåda", "Husdjur", 1, 20.00);
        Item item5 = new Item("Samlarbilder", "Memorabilia", 100, 950.00);
        Item item6 = new Item("Vinterjacka", "Kläder", 1, 1300.00);
        Item item7 = new Item("Elgitarr", "Musikinstrument", 1, 3500.00);
        Item item8 = new Item("Laptop", "Hemelektronik", 1, 4300.00);
        Item item9 = new Item("Snowracer", "Friluftsliv", 1, 500.00);
        Item item10 = new Item("Strumpor", "Kläder", 10, 79.00);

        user1.addItem(item1);
        user1.addItem(item2);
        user1.addItem(item3);
        user2.addItem(item4);
        user2.addItem(item5);
        user2.addItem(item6);
        user3.addItem(item7);
        user3.addItem(item8);
        user3.addItem(item9);
        user3.addItem(item10);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);


    }


}
