package ru.csit.shiryaev_lab.core.entities;

import ru.csit.shiryaev_lab.core.data.Items;


public class Item {
    public int id;
    public String name = "";
    public String description = "";
    public int count = 1;

    public Item () {
        id = 0;
    }

    public Item (Item other) {
        id = other.id;
        merge(other);
    }

    public void merge (Item other) {
        name = other.name;
        description = other.description;
        count = other.count;
    }

    public void save () {
        Items.update(this);
    }
}
