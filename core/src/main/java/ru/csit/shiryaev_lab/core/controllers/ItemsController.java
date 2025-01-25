package ru.csit.shiryaev_lab.core.controllers;

import org.springframework.web.bind.annotation.*;
import ru.csit.shiryaev_lab.core.data.Items;
import ru.csit.shiryaev_lab.core.entities.Item;
import ru.csit.shiryaev_lab.core.payloads.ItemEditPayload;

import java.util.List;


@RestController
public class ItemsController {
    @GetMapping("/items")
    public final List<Item> getItems () {
        return Items.getAll();
    }

    @PutMapping("/items")
    public final String createItem (@RequestBody ItemEditPayload payload) {
        var item = new Item();
        item.name = payload.name();
        item.description = payload.description();

        Items.insert(item);
        return "OK";
    }

    @GetMapping("/items/{id}")
    public final Item getItem (@PathVariable int id) {
        return Items.findById(id);
    }

    @PatchMapping("/items/{id}")
    public final String updateItem (@PathVariable int id, @RequestBody ItemEditPayload payload) {
        var item = Items.findById(id);
        if (item == null) {
            return "No such item";
        }

        if (payload.name() != null) {
            item.name = payload.name();
        }

        if (payload.description() != null) {
            item.description = payload.description();
        }

        item.save();
        return "OK";
    }

    @DeleteMapping("/items/{id}")
    public final String deleteItem (@PathVariable int id) {
        Items.deleteById(id);
        return "OK";
    }
}
