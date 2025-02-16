package ru.csit.shiryaev_lab.gateway;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.csit.shiryaev_lab.gateway.controllers.ItemsController;
import ru.csit.shiryaev_lab.gateway.payloads.ItemEditPayload;


@SpringBootTest(properties = "remote.core=http://127.0.0.1:8081")
public class FullCycleTest {
    @Autowired
    private ItemsController controller;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void test () throws JsonProcessingException {
        var newItemPayload = new ItemEditPayload("New item", "Created via gateway");
        assertEquals("OK", controller.createItem(newItemPayload).block());

        var itemListRaw = controller.getItems().block();
        var itemList = jsonMapper.readTree(itemListRaw);
        assertTrue(itemList.size() > 1);

        var lastItem = itemList.get(itemList.size() - 1);
        assertNotNull(lastItem);
        assertEquals(newItemPayload.name(), lastItem.get("name").asText());
        assertEquals(newItemPayload.description(), lastItem.get("description").asText());

        var lastItemId = lastItem.get("id").asText();
        var updateItemPayload = new ItemEditPayload("New name", "New description");
        assertEquals("OK", controller.updateItem(lastItemId, updateItemPayload).block());

        var lastItemByIdRaw = controller.getItem(lastItemId).block();
        var lastItemById = jsonMapper.readTree(lastItemByIdRaw);
        assertNotNull(lastItemById);
        assertEquals(updateItemPayload.name(), lastItemById.get("name").asText());
        assertEquals(updateItemPayload.description(), lastItemById.get("description").asText());

        var expectedListSize = itemList.size() - 1;
        assertEquals("OK", controller.deleteItem(lastItemId).block());
        itemListRaw = controller.getItems().block();
        itemList = jsonMapper.readTree(itemListRaw);
        assertEquals(expectedListSize, itemList.size());
    }
}
