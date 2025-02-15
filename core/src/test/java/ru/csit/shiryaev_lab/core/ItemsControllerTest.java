package ru.csit.shiryaev_lab.core;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.csit.shiryaev_lab.core.controllers.ItemsController;
import ru.csit.shiryaev_lab.core.data.Items;
import ru.csit.shiryaev_lab.core.entities.Item;
import ru.csit.shiryaev_lab.core.payloads.ItemEditPayload;
import java.util.List;
import java.util.Optional;


class ItemsControllerTest {
    @Mock
    private Items items;

    @InjectMocks
    private ItemsController itemsController;

    @BeforeEach
    void setup () {
        MockitoAnnotations.openMocks(this);
    }

    Item mockOneItem () {
        var item1 = new Item();
        item1.id = 1;
        item1.name = "Item 1";
        item1.description = "Description of 1";

        when(items.findById(anyInt())).thenReturn(Optional.empty());
        when(items.findById(1)).thenReturn(Optional.of(item1));
        return item1;
    }

    ArgumentCaptor<Item> mockItemSave () {
        when(items.save(any(Item.class)))
            .thenAnswer(invocation -> invocation.getArguments()[0]);

        return ArgumentCaptor.forClass(Item.class);
    }

    ArgumentCaptor<Integer> mockItemDelete () {
        doNothing().when(items).deleteById(anyInt());

        return ArgumentCaptor.forClass(Integer.class);
    }

    @Test
    void testGetItems () {
        var item1 = new Item();
        item1.id = 1;
        item1.name = "Item 1";
        item1.description = "Description of 1...";

        var item2 = new Item();
        item2.id = 2;
        item2.name = "Item 2";
        item2.description = "Description of 2...";

        when(items.findAll()).thenReturn(List.of(item1, item2));

        List<Item> result = itemsController.getItems();
        assertEquals(2, result.size());

        assertEquals(item1.name, result.get(0).name);
        assertEquals(item1.description, result.get(0).description);

        assertEquals(item2.name, result.get(1).name);
        assertEquals(item2.description, result.get(1).description);
    }

    @Test
    void testCreateItem () {
        var savedItem = mockItemSave();
        var payload = new ItemEditPayload("New item", "Description of new item");
        assertEquals("OK", itemsController.createItem(payload));
        verify(items, times(1)).save(savedItem.capture());

        var newItem = savedItem.getValue();
        assertEquals(payload.name(), newItem.name);
        assertEquals(payload.description(), newItem.description);
    }

    @Test
    void testGetItem () {
        var item1 = mockOneItem();

        var result1 = itemsController.getItem(1);
        assertNotNull(result1);
        assertEquals(item1.name, result1.name);
        assertEquals(item1.description, result1.description);

        var result2 = itemsController.getItem(2);
        assertNull(result2);
    }

    @Test
    void testUpdateItemExists() {
        mockOneItem();
        var savedItem = mockItemSave();

        var payload = new ItemEditPayload("New name", "New description");
        assertEquals("OK", itemsController.updateItem(1, payload));
        verify(items, times(1)).save(savedItem.capture());

        assertEquals(payload.name(), savedItem.getValue().name);
        assertEquals(payload.description(), savedItem.getValue().description);

        assertEquals("No such item", itemsController.updateItem(2, payload));
    }

    @Test
    void testDeleteItem() {
        var deletedItem = mockItemDelete();
        assertEquals("OK", itemsController.deleteItem(1));

        verify(items, times(1)).deleteById(deletedItem.capture());
        assertEquals(1, deletedItem.getValue());
    }
}
