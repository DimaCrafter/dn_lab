package ru.csit.shiryaev_lab.core.controllers;

import org.springframework.web.bind.annotation.*;
import ru.csit.shiryaev_lab.core.data.Items;
import ru.csit.shiryaev_lab.core.entities.Item;
import ru.csit.shiryaev_lab.core.payloads.ItemEditPayload;

import java.util.List;


@RestController
public class ItemsController {
    private final Items items;

    public ItemsController (Items items) {
        this.items = items;
    }

    @GetMapping("/items")
    public final List<Item> getItems () {
        return items.findAll();
    }

    @PutMapping("/items")
    public final String createItem (@RequestBody ItemEditPayload payload) {
        var item = new Item();
        item.name = payload.name();
        item.description = payload.description();

        items.save(item);
        return "OK";
    }

    @GetMapping("/items/{id}")
    public final Item getItem (@PathVariable int id) {
        return items.findById(id).orElse(null);
    }

    @PatchMapping("/items/{id}")
    public final String updateItem (@PathVariable int id, @RequestBody ItemEditPayload payload) {
        var itemRes = items.findById(id);
        if (itemRes.isEmpty()) {
            return "No such item";
        }

        var item = itemRes.get();
        if (payload.name() != null) {
            item.name = payload.name();
        }

        if (payload.description() != null) {
            item.description = payload.description();
        }

        items.save(item);
        return "OK";
    }

    @DeleteMapping("/items/{id}")
    public final String deleteItem (@PathVariable int id) {
        items.deleteById(id);
        return "OK";
    }
}
