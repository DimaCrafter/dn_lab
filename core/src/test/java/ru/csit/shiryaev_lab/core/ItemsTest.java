package ru.csit.shiryaev_lab.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.csit.shiryaev_lab.core.data.Items;
import ru.csit.shiryaev_lab.core.entities.Item;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase
public class ItemsTest {
    @Autowired
    private Items items;

    List<Item> insertTwoItems () {
        var item1 = new Item();
        item1.name = "Item 1";
        item1.description = "Description of 1...";
        items.save(item1);

        var item2 = new Item();
        item2.name = "Item 2";
        item2.description = "Description of 2...";
        items.save(item2);

        return List.of(item1, item2);
    }

    @Test
    void testFindAll () {
        var expectedItems = insertTwoItems();
        var actualItems = items.findAll();
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void testCreate () {
        insertTwoItems();

        var newItem = new Item();
        newItem.name = "Item 3";
        newItem.description = "Description of 3...";
        items.save(newItem);

        var savedItem = items.findById(newItem.id);
        assertTrue(savedItem.isPresent());
        assertEquals(newItem, savedItem.get());
    }

    @Test
    void testFindById () {
        var lastItem = insertTwoItems().get(1);

        var item2 = items.findById(lastItem.id);
        assertTrue(item2.isPresent());
        assertEquals(lastItem, item2.get());

        var item3 = items.findById(lastItem.id + 1);
        assertTrue(item3.isEmpty());
    }

    @Test
    void testUpdate () {
        var item2 = insertTwoItems().get(1);
        item2.name = "New name";
        items.save(item2);

        var updatedItem = items.findById(item2.id);
        assertTrue(updatedItem.isPresent());
        assertEquals(item2.name, updatedItem.get().name);
    }

    @Test
    void testDeleteById () {
        var lastItem = insertTwoItems().get(1);
        items.deleteById(lastItem.id);
        assertTrue(items.findById(lastItem.id).isEmpty());
    }
}
