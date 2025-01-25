package ru.csit.shiryaev_lab.core.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.csit.shiryaev_lab.core.entities.Item;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Items {
    private static File file = new File("items.json");
    private static ObjectMapper json = new ObjectMapper();
    private static DataFileShape<Item> content;

    static {
        try {
            if (file.exists()) {
                content = json.readValue(file, new TypeReference<DataFileShape<Item>>() {});
            } else {
                content = new DataFileShape<>();
                save();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to open items.json", ex);
        }
    }

    private static void save () {
        try {
            json.writeValue(file, content);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save items.json", ex);
        }
    }

    public static List<Item> getAll () {
        return content.data;
    }

    public static void insert (Item item) {
        item.id = content.nextId++;
        content.data.add(item);
        save();
    }

    private static Item _findById (int id) {
        for (var item: content.data) {
            if (item.id == id) {
                return item;
            }
        }

        return null;
    }

    public static Item findById (int id) {
        var item = _findById(id);
        return item == null ? null : new Item(item);
    }

    public static void update (Item update) {
        var storedItem = _findById(update.id);
        if (storedItem != null) {
            storedItem.merge(update);
            save();
        }
    }

    public static void deleteById (int id) {
        var item = _findById(id);
        if (item != null) {
            content.data.remove(item);
            save();
        }
    }
}
